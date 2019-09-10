$(window).on('beforeunload', function(){
		$(".blank").fadeIn(300);
		$("#main-wrapper-menu,#A1-main,#A2,#A3,#A4,#A5,#A6,#A7").animate({'opacity':'0'},300);
  		$(window).delay(1000).scrollTop(0);
	});
	
   $(document).ready(function() {
	
	$.localScroll();
	
	$(".blank").delay(1000).fadeOut(300);
	$("#wrapper-head").delay(2000).animate({'opacity':'1'},500);
	$("#wrapper-logo").delay(2000).animate({'opacity':'1'},300);
	$("#wrapper-plus").delay(2000).animate({'opacity':'1'},300);
});

$(function() {
	var bar = $('#wrapper-head');
	var opacity = bar.css('opacity');
	var top = bar.css('top');
	
	$(window).scroll(function() {
		if($(this).scrollTop() > 100) {
			bar.stop().animate({ opacity :'0', top :'-10%'},250);
		} 
	});
		
	$(window).scroll(function() {
		if($(this).scrollTop() < 100) {
			bar.stop().animate({ opacity :'1', top :'0'},250);
		}	
	});
});



/*BUTTON ARROW*/

$(document).on('touchstart mouseover', '#fleche1', function(event){
        event.stopPropagation();
        event.preventDefault();
        if(event.handled !== true) {
			 
			 $("#fleche1").stop().animate({'top':'92.5%'},200);

            event.handled = true;
        } else {
            return false;
        }
});

$(document).on('touchend mouseout', '#fleche1', function(event){
        event.stopPropagation();
        event.preventDefault();
        if(event.handled !== true) {
			 
			 $("#fleche1").stop().animate({'top':'90%'},200);
			 
            event.handled = true;
        } else {
            return false;
        }
});


/*BUTTON LOGO*/

$(document).on('touchstart mouseover', '#wrapper-logo', function(event){
        event.stopPropagation();
        event.preventDefault();
        if(event.handled !== true) {
			 
			 $("#wrapper-logo").stop().animate({'scale':'1.1','opacity':'0.5'},200);

            event.handled = true;
        } else {
            return false;
        }
});

$(document).on('touchend mouseout', '#wrapper-logo', function(event){
        event.stopPropagation();
        event.preventDefault();
        if(event.handled !== true) {
			 
			 $("#wrapper-logo").stop().animate({'scale':'1','opacity':'1'},200);

            event.handled = true;
        } else {
            return false;
        }
});

/*BUTTON PLUS*/

$(document).on('touchstart mouseover', '#wrapper-plus', function(event){
        event.stopPropagation();
        event.preventDefault();
        if(event.handled !== true) {
			 
			 $("#wrapper-plus").stop().animate({'opacity':'0.75','scale':'1.1'},150);

            event.handled = true;
        } else {
            return false;
        }
});

$(document).on('touchend mouseout', '#wrapper-plus', function(event){
        event.stopPropagation();
        event.preventDefault();
        if(event.handled !== true) {
			 
			 $("#wrapper-plus").stop().animate({'opacity':'1','scale':'1'},150);
			 
            event.handled = true;
        } else {
            return false;
        }
});

/*BUTTON CROSS*/

$(document).on('touchstart mouseover', '#wrapper-cross', function(event){
        event.stopPropagation();
        event.preventDefault();
        if(event.handled !== true) {
			 
			 $("#wrapper-cross").stop().animate({'opacity':'0.75','scale':'1.1'},150);

            event.handled = true;
        } else {
            return false;
        }
});

$(document).on('touchend mouseout', '#wrapper-cross', function(event){
        event.stopPropagation();
        event.preventDefault();
        if(event.handled !== true) {
			 
			 $("#wrapper-cross").stop().animate({'opacity':'1','scale':'1'},150);
			 
            event.handled = true;
        } else {
            return false;
        }
});




/*BUTTON GOOGLE*/

$(document).on('touchstart mouseover', '#main-google', function(event){
        event.stopPropagation();
        event.preventDefault();
        if(event.handled !== true) {

			 $("#google").stop().css({'color':'#FFF'});
			 $("#cadre-google").stop().css({'background-color':'#EA6E75'});

            event.handled = true;
        } else {
            return false;
        }
});

$(document).on('touchend mouseout', '#main-google', function(event){
        event.stopPropagation();
        event.preventDefault();
        if(event.handled !== true) {

			 $("#google").stop().css({'color':'#66676D'});
			 $("#cadre-google").stop().css({'background-color':'transparent'});

            event.handled = true;
        } else {
            return false;
        }
});

/*BUTTON PAGE2*/

$(document).on('touchstart mouseover', '#main-page2', function(event){
        event.stopPropagation();
        event.preventDefault();
        if(event.handled !== true) {
			 
			 $("#page2").stop().css({'background-color':'transparent','border-color':'#444549'});
			 $("#text-page2").stop().css({'color':'#444549'},250);
			 $("#main-page2").stop().animate({'scale':'1.1'},250);

            event.handled = true;
        } else {
            return false;
        }
});

$(document).on('touchend mouseout', '#main-page2', function(event){
        event.stopPropagation();
        event.preventDefault();
        if(event.handled !== true) {
			 
			 $("#page2").stop().css({'background-color':'#9C9C9C','border-color':'#9C9C9C'});
		     $("#text-page2").stop().css({'color':'#FFF'},250);
	         $("#main-page2").stop().animate({'scale':'1'},250);
			 
            event.handled = true;
        } else {
            return false;
        }
});

/*BUTTON PAGE3*/

$(document).on('touchstart mouseover', '#main-page3', function(event){
        event.stopPropagation();
        event.preventDefault();
        if(event.handled !== true) {
			 
			 $("#page3").stop().css({'background-color':'transparent','border-color':'#444549'});
			 $("#text-page3").stop().css({'color':'#444549'},250);
			 $("#main-page3").stop().animate({'scale':'1.1'},250);

            event.handled = true;
        } else {
            return false;
        }
});

$(document).on('touchend mouseout', '#main-page3', function(event){
        event.stopPropagation();
        event.preventDefault();
        if(event.handled !== true) {
			 
			 $("#page3").stop().css({'background-color':'#9C9C9C','border-color':'#9C9C9C'});
		     $("#text-page3").stop().css({'color':'#FFF'},250);
	         $("#main-page3").stop().animate({'scale':'1'},250);
			 
            event.handled = true;
        } else {
            return false;
        }
});

/*BUTTON PAGE4*/

$(document).on('touchstart mouseover', '#main-page4', function(event){
        event.stopPropagation();
        event.preventDefault();
        if(event.handled !== true) {
			 
			 $("#page4").stop().css({'background-color':'transparent','border-color':'#444549'});
			 $("#text-page4").stop().css({'color':'#444549'},250);
			 $("#main-page4").stop().animate({'scale':'1.1'},250);

            event.handled = true;
        } else {
            return false;
        }
});

$(document).on('touchend mouseout', '#main-page4', function(event){
        event.stopPropagation();
        event.preventDefault();
        if(event.handled !== true) {
			 
			 $("#page4").stop().css({'background-color':'#9C9C9C','border-color':'#9C9C9C'});
		     $("#text-page4").stop().css({'color':'#FFF'},250);
	         $("#main-page4").stop().animate({'scale':'1'},250);
			 
            event.handled = true;
        } else {
            return false;
        }
});

/*BUTTON HEADER LEARN MORE*/

$(document).on('touchstart mouseover', '#wrapper-learnmore', function(event){
        event.stopPropagation();
        event.preventDefault();
        if(event.handled !== true) {
			 
			 $(".bouton-learnmore").stop().css({'background-color':'#FFF'});
			 $(".text-learnmore").stop().css({'color':'#333'},250);

            event.handled = true;
        } else {
            return false;
        }
});

$(document).on('touchend mouseout', '#wrapper-learnmore', function(event){
        event.stopPropagation();
        event.preventDefault();
        if(event.handled !== true) {
			 
			 $(".bouton-learnmore").stop().css({'background-color':'transparent'});
		     $(".text-learnmore").stop().css({'color':'#FFF'},250);
			 
            event.handled = true;
        } else {
            return false;
        }
});