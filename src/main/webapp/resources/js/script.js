$(document).ready(function() {
		
	// flash messages
    $('.flash-message').delay(5000).fadeOut();
    $('.flash-close').click(function() {
    	$(this).parent().dequeue().fadeOut();
    });
    
    // article form - hide/show select with users
    $('input[name="access"][type="radio"]').each(function(index, element) {
    	if ( $(this).prop("checked") && $(this).val() == 'PUB' ) {
    		$('.permitted-users').hide();
    		return false;
    	}
    });
    
	$('input[name="access"][type="radio"]').click(function() {
		if ( $(this).val() == 'PRV' ) {
			$('.permitted-users').show();
		} else {
			$('.permitted-users').hide();
		}
	});
	
});