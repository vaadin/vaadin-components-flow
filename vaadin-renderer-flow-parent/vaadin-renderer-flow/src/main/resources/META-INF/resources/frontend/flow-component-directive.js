import { noChange } from 'lit';
import { until } from 'lit/directives/until.js';
import { directive, Directive, PartType } from 'lit/directive.js';

class FlowComponentDirective extends Directive {
  constructor(partInfo) {
    super(partInfo);
    if (partInfo.type !== PartType.CHILD) {
      throw new Error(`${this.constructor.directiveName}() can only be used in child bindings`);
    }
  }

  update(part, [appid, nodeid]) {
    this.updateContent(part, appid, nodeid);
    return noChange;
  }

  updateContent(part, appid, nodeid) {
    const { parentNode, startNode } = part;

    const hasNewNodeId = nodeid !== undefined && nodeid !== null;
    const newNode = hasNewNodeId ? this.getNewNode(appid, nodeid) : null;
    const oldNode = this.getOldNode(part);

    if (hasNewNodeId && !newNode) {
      // If the node is not found, try again later.
      setTimeout(() => this.updateContent(part, appid, nodeid));
    } else if (oldNode === newNode) {
      return;
    } else if (oldNode && newNode) {
      parentNode.replaceChild(newNode, oldNode);
    } else if (oldNode) {
      parentNode.removeChild(oldNode);
    } else if (newNode) {
      startNode.after(newNode);
    }
  }

  getNewNode(appid, nodeid) {
    return window.Vaadin.Flow.clients[appid].getByNodeId(nodeid);
  }

  getOldNode(part) {
    const { startNode, endNode } = part;
    if (startNode.nextSibling === endNode) {
      return;
    }
    return startNode.nextSibling;
  }
}

const flowComponentDirectiveInternal = directive(FlowComponentDirective);

/**
 * Renders the given flow component node asynchronously.
 *
 * WARNING: This directive is not intended for public use.
 *
 * @param {string} appid
 * @param {number} nodeid
 * @private
 */
export const flowComponentDirective = (appid, nodeid) => {
  return flowComponentDirectiveInternal(appid, nodeid);
};
