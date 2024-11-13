function disableOnClickListener({currentTarget: element}) {
  if (element.hasAttribute('disableOnClick')) {
    requestAnimationFrame(() => element.disabled = true);
  }
}

window.Vaadin.Flow.disableOnClick = {
  initDisableOnClick: (element) => {
    if (!element.__hasDisableOnClickListener) {
      element.addEventListener('click', disableOnClickListener);
      element.__hasDisableOnClickListener = true;
    }
  }
}
