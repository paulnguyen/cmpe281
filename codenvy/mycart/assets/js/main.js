/* Author: Stephen McKinney - Wojo Design */

$(function(){
	//encode html entities
	function htmlDecode(value) {
	    if (value) {
	        return $('<div />').text(value).html();
	    } else {
	        return '';
	    }
	}
	
	$('pre.html,pre.feature-html').each(function(){
		$(this).html(htmlDecode($(this).html()));
	});
	
	$("#demo .simpleCart_shelfItem").mouseenter(function(event) {
		$(this).find('.tooltip').fadeIn(200);
	});
	$("#demo .simpleCart_shelfItem").mouseleave(function(event) {
		$(this).find('.tooltip').fadeOut(200);
	});
	$("header .cartInfo").toggle(function(){
		$("#cartPopover").show();
		$("header .cartInfo").addClass('open');
	}, function(){
		$("#cartPopover").hide();
		$("header .cartInfo").removeClass('open');
	});
	
	
	$("#demoShelf .simpleCart_shelfItem:eq(0)").css('left', '310px');
	$("#demoShelf .simpleCart_shelfItem:eq(1)").css('left', '171px');
	$("#demoShelf .simpleCart_shelfItem:eq(2)").css('left', '29px');
	$("#demoShelf .simpleCart_shelfItem").delay(500).animateStep({css:{top:'0px'},delay:100,speed:100});
	$("#demoShelf .simpleCart_shelfItem").click(function(){
		$(".intro").css('overflow','visible');
		var clone = $(this).clone(),
			position = $(this).position(),
			bezier_params = {
		    start: { 
		      x: position.left, 
		      y: 0, 
		      angle: -90
		    },  
		    end: { 
		      x:310,
		      y:-210, 
		      angle: 180, 
		      length: .2
		    }
		  };
		
		clone.appendTo('#demoShelf');
		clone.find('.tooltip').hide();
		clone.addClass('addDemoAnimation');
		clone.animate({path : new $.path.bezier(bezier_params)}, 600);
		
	});
	
	
	
	
	/*******************************
		Features Page
	*******************************/
	$("#btnViewAll").click(function(){
		$(this).next().slideToggle(300);
		$(this).toggleClass('active');
	});
	
	
	$("#gatewayDemoList :radio").change(function(){
		$("#gatewayDemoList .active").removeClass('active');
		$("#gatewayDemoList input:checked").parent().addClass('active');
		var newGateway = $("#gatewayDemoList input:checked").attr('id').split('-')[1];
		console.log(newGateway);
		if (newGateway === 'paypal') {
			simpleCart({
			    checkout: { 
			        type: "PayPal" , 
			        email: "you@yours.com" 
			    } 
			});
			
		} else if (newGateway === 'amazon') {
			simpleCart({
			    checkout: { 
			        type: "AmazonPayments" , 
			        merchant_signature: "XXXXXXXXX" ,
			        merchant_id: "XXX",
			        aws_access_key_id: "XXX" ,
			        method: "GET" ,
			        sandbox: true ,
			        weight_unit: "lb" 
			    } 
			});
			
		} else if (newGateway ==='google') {
			simpleCart({
			    checkout: { 
			        type: "GoogleCheckout" , 
			        merchantID: "761936722557277" ,
			        method: "POST" 
			    } 
			});
			
		}
		
	});
	
	$("#features-performance-visual").parent().hover(function(){
		$("#features-performance-visual #needle").addClass('moveNeedle');
	}, function(){
		$("#features-performance-visual #needle").removeClass('moveNeedle');
	});
	
	//code samples
	$("pre.feature-html").snippet("html",{
		style: "dark",
		showNum: false,
		menue: false
		
	});
	$("select").uniform();
	$("#feature-code-select").change(function(){
		$("#codeSnippets .snippet-container").hide();
		$("#codeSnippets .snippet-container").eq($(this).find(':selected').index()).show();
	});
	
	/*******************************
		Documentation
	*******************************/
	
	
	
	$("pre.html").snippet("html",{
		style: "kwrite",
		clipboard: "/assets/js/libs/ZeroClipboard.swf"
		
	});
	$("pre.css").snippet("css",{
		style: "kwrite",
		clipboard: "/assets/js/libs/ZeroClipboard.swf"
		
	});
	$("pre.js").snippet("javascript",{
		style: "kwrite",
		clipboard: "/assets/js/libs/ZeroClipboard.swf"
		
	});
	$("pre.php").snippet("php",{
		style: "kwrite",
		clipboard: "/assets/js/libs/ZeroClipboard.swf"
		
	});
	
	
});























