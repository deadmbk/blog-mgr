$(document).ready(function() {
	
	
	// flash messages
    $('.flash-message').delay(5000).fadeOut();
    $('.flash-close').click(function() {
    	$(this).parent().dequeue().fadeOut();
    });
    
});