function inizializzaScroll(dragEl, scroll) {
	
	if (scroll.scrollWidth>window.innerWidth-32) {
		dragEl.style.width=((window.innerWidth-32)/(scroll.scrollWidth/(window.innerWidth)))+"px";
		dragEl.style.visibility="visible";
		
		var dragging = false;
		var elDragPointOffset = 0;

		dragEl.addEventListener('mousedown', function(evt) {
			evt.preventDefault();
			evt.stopPropagation();
			dragging = true;

			var elBounds = dragEl.getBoundingClientRect();
			elDragPointOffset = evt.pageX - elBounds.left;
		});


		window.addEventListener('mouseup', function(evt) {
			dragging = false;
		});


		window.addEventListener('mousemove', function(evt) {
			if (dragging) {

				var mouseX = evt.pageX;

				var elBounds = dragEl.getBoundingClientRect();
				var dragElWidth = elBounds.right - elBounds.left;

				var parentContainer = dragEl.parentElement;

				var parentBounds = parentContainer.getBoundingClientRect();
				var parentLeft = parentBounds.left;
				var parentWidth = parentBounds.right - parentBounds.left;

				var newDragElLeftValue = mouseX - parentLeft - elDragPointOffset;
				console.log(newDragElLeftValue);
				if (newDragElLeftValue < 0) {
					dragEl.style.left = 0;
					newDragElLeftValue = 0;
				}
				else if (newDragElLeftValue > (parentWidth - dragElWidth)) {
					dragEl.style.left = parentWidth - dragElWidth;
					newDragElLeftValue = parentWidth - dragElWidth;
				}
				else
					dragEl.style.left = newDragElLeftValue + 'px';

				var scrollMaxWidth = scroll.scrollWidth;
				var screenSize = window.innerWidth - dragElWidth;
				console.log("scrollMaxWidth " + scrollMaxWidth);
				console.log("scrollMaxWidth-screenSize " + (scrollMaxWidth - screenSize));
				console.log("scroll size: " + dragElWidth);
				console.log("window.innerWidth " + window.innerWidth);
				console.log("newDragElLeftValue " + newDragElLeftValue);
				console.log("scrollMissing " + (dragElWidth - newDragElLeftValue));
				var r = (scrollMaxWidth - window.innerWidth + 16) / (parentWidth - dragElWidth - 16);
				r = r * 0.990;
				console.log("r " + r);
				console.log("newDragElLeftValue*r " + newDragElLeftValue * r);
				console.log("--------------------------------------------------");
				scroll.scrollTo(newDragElLeftValue * r, 0);
			}
		});
	}
}