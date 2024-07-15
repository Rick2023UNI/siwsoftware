function mostraNascondiInformazione() {
	var informazione=document.getElementById("informazione");
	if (informazione.classList.contains("nascosto")) {
		informazione.classList.remove("nascosto");
	}
	else {
		informazione.classList.add("nascosto");
	}
}


$(document).ready(function() {
	var dragEl = document.getElementsByClassName('scroll-orizzontale')[0];

var dragging = false;
var elDragPointOffset = 0;

dragEl.addEventListener('mousedown', function(evt){
	evt.preventDefault();
    evt.stopPropagation();
  dragging = true;
  
  var elBounds = dragEl.getBoundingClientRect();
  elDragPointOffset = evt.pageX - elBounds.left; 
});


window.addEventListener('mouseup', function(evt){
  dragging = false;
});


window.addEventListener('mousemove', function(evt){
  if(dragging){
    
    var mouseX = evt.pageX;
    
    var elBounds = dragEl.getBoundingClientRect();
    var dragElWidth = elBounds.right - elBounds.left;
    
    var parentContainer = dragEl.parentElement;
    
    var parentBounds = parentContainer.getBoundingClientRect();
    var parentLeft = parentBounds.left;
    var parentWidth = parentBounds.right - parentBounds.left;
    
    var newDragElLeftValue = mouseX - parentLeft - elDragPointOffset;
    
    if(newDragElLeftValue < 0)
      dragEl.style.left = 0; 
    else if(newDragElLeftValue > (parentWidth - dragElWidth))
      dragEl.style.left = parentWidth - dragElWidth; 
    else
      dragEl.style.left = newDragElLeftValue + 'px';
    
    var scrollMaxWidth=document.getElementsByClassName('sviluppatori')[2].scrollWidth;
    var screenSize=window.innerWidth-dragEl.scrollWidth;
    console.log("scrollMaxWidth "+scrollMaxWidth);
    console.log("scroll size: "+dragEl.scrollWidth);
    console.log("window.innerWidth "+window.innerWidth);
    console.log("newDragElLeftValue "+newDragElLeftValue);
    var r=(scrollMaxWidth-screenSize)/screenSize;
    console.log("r "+r);
    r=1.80;
    console.log("newDragElLeftValue*r "+newDragElLeftValue*r);
    console.log("--------------------------------------------------");
    if (newDragElLeftValue>0)
    	document.getElementsByClassName("sviluppatori")[2].scrollTo(newDragElLeftValue*r, 0); // values are x,y-offset
  }
});
	});
	