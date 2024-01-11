import { aTimeout, expect, fixtureSync, nextFrame } from '@open-wc/testing';
import {
  init,
  GRID_CONNECTOR_ROOT_REQUEST_DELAY
} from './shared.js';
import type { FlowGrid } from './shared.js';

const ROOT_SIZE = 200;
const PAGE_SIZE = 50;

describe('grid connector - data range', () => {
  let grid: FlowGrid;
  let lastRequestedRange: [number, number] | null;

  function setRootItemsRange(start: number, count: number) {
    const items = Array.from({ length: ROOT_SIZE }, (_, i) => ({ key: `${i}`, name: `Item-${i}` }));

    if (lastRequestedRange) {
      grid.$connector.clear(lastRequestedRange[0], lastRequestedRange[1]);
    }

    count = Math.min(count, ROOT_SIZE - start);
    grid.$connector.set(start, items.slice(start, start + count));
    grid.$connector.confirm(-1);
    lastRequestedRange = [start, count];
  }

  function expectRequestedRange(range: [number, number]) {
    expect(grid.$server.setRequestedRange).to.be.calledOnce;
    expect(grid.$server.setRequestedRange.args[0]).to.eql(range);
  }

  function processRequestedRange() {
    const range = grid.$server.setRequestedRange.args[0];
    setRootItemsRange(range[0], range[1]);
    grid.$server.setRequestedRange.resetHistory();
  }

  beforeEach(async () => {
    lastRequestedRange = null;

    grid = fixtureSync(`
      <vaadin-grid>
        <vaadin-grid-column path="name"></vaadin-grid-column>
      </vaadin-grid>
    `);

    init(grid);
    await nextFrame();

    grid.pageSize = PAGE_SIZE;
    grid.$connector.updateSize(ROOT_SIZE);

    await aTimeout(GRID_CONNECTOR_ROOT_REQUEST_DELAY);
    expectRequestedRange([0, PAGE_SIZE]);
    processRequestedRange();
  });

  it('should request correct ranges when scrolling (start <-> end)', async () => {
    grid.scrollToIndex(ROOT_SIZE - 1);
    await aTimeout(GRID_CONNECTOR_ROOT_REQUEST_DELAY);
    expectRequestedRange([ROOT_SIZE - PAGE_SIZE, PAGE_SIZE * 2]);

    processRequestedRange();

    grid.scrollToIndex(0);
    await aTimeout(GRID_CONNECTOR_ROOT_REQUEST_DELAY);
    expectRequestedRange([0, PAGE_SIZE]);
  });

  it('should request correct range when size decreases after scrolling (start -> end)', async () => {
    const newSize = ROOT_SIZE / 2;
    grid.scrollToIndex(ROOT_SIZE - 1);
    grid.$connector.updateSize(newSize);
    await aTimeout(GRID_CONNECTOR_ROOT_REQUEST_DELAY);
    expectRequestedRange([newSize - PAGE_SIZE, PAGE_SIZE * 2]);
  });

  it('should request correct ranges when scrolling (start -> middle -> end)', async () => {
    grid.scrollToIndex(ROOT_SIZE / 2);
    await aTimeout(GRID_CONNECTOR_ROOT_REQUEST_DELAY);
    expectRequestedRange([ROOT_SIZE / 2 - PAGE_SIZE, PAGE_SIZE * 2]);

    processRequestedRange();

    grid.scrollToIndex(ROOT_SIZE - 1);
    await aTimeout(GRID_CONNECTOR_ROOT_REQUEST_DELAY);
    expectRequestedRange([ROOT_SIZE - PAGE_SIZE, PAGE_SIZE * 2]);
  });

  it('should request correct ranges when scrolling (end -> middle -> start)', async () => {
    grid.scrollToIndex(ROOT_SIZE - 1);
    await aTimeout(GRID_CONNECTOR_ROOT_REQUEST_DELAY);
    processRequestedRange();

    grid.scrollToIndex(ROOT_SIZE / 2);
    await aTimeout(GRID_CONNECTOR_ROOT_REQUEST_DELAY);
    expectRequestedRange([ROOT_SIZE / 2 - PAGE_SIZE, PAGE_SIZE * 2]);

    processRequestedRange();

    grid.scrollToIndex(0);
    await aTimeout(GRID_CONNECTOR_ROOT_REQUEST_DELAY);
    expectRequestedRange([0, PAGE_SIZE]);
  });

  it('should debounce range requests when scrolling fast', async () => {
    grid.scrollToIndex(ROOT_SIZE / 2);
    grid.scrollToIndex(ROOT_SIZE - 1);
    await aTimeout(GRID_CONNECTOR_ROOT_REQUEST_DELAY);
    expectRequestedRange([ROOT_SIZE - PAGE_SIZE, PAGE_SIZE * 2]);
  });

  it('should request correct ranges when scrolling fast (start <-> end)', async () => {
    grid.scrollToIndex(ROOT_SIZE - 1);
    grid.scrollToIndex(0);
    await aTimeout(GRID_CONNECTOR_ROOT_REQUEST_DELAY);
    expectRequestedRange([ROOT_SIZE - PAGE_SIZE, PAGE_SIZE]);

    processRequestedRange();

    await aTimeout(GRID_CONNECTOR_ROOT_REQUEST_DELAY);
    expectRequestedRange([0, PAGE_SIZE]);
  });

  it('should request correct range when size descreases after scrolling fast (start <-> end)', async () => {
    grid.scrollToIndex(ROOT_SIZE - 1);
    grid.scrollToIndex(0);
    const newSize = ROOT_SIZE / 2;
    grid.$connector.updateSize(newSize);
    await aTimeout(GRID_CONNECTOR_ROOT_REQUEST_DELAY);
    expectRequestedRange([newSize - PAGE_SIZE, PAGE_SIZE]);
  });
});
