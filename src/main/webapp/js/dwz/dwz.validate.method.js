/**
 * @requires jquery.validate.js
 * @author ZhangHuihua@msn.com
 */
(function($){
	if ($.validator) {
		$.validator.addMethod("alphanumeric", function(value, element) {
			return this.optional(element) || /^\w+$/i.test(value);
		}, "Letters, numbers or underscores only please");
		
		$.validator.addMethod("lettersonly", function(value, element) {
			return this.optional(element) || /^[a-z]+$/i.test(value);
		}, "Letters only please"); 
		
		$.validator.addMethod("phone", function(value, element) {
			return this.optional(element) || /^[0-9 \(\)]{7,30}$/.test(value);
		}, "Please specify a valid phone number");
		
		$.validator.addMethod("postcode1", function(value, element) {
			return this.optional(element) || /^[0-9 A-Za-z]{5,20}$/.test(value);
		}, "Please specify a valid postcode");
		
		$.validator.addMethod("date", function(value, element) {
			value = value.replace(/\s+/g, "");
			if (String.prototype.parseDate){
				var $input = $(element);
				var pattern = $input.attr('format') || 'yyyy-MM-dd';
	
				return !$input.val() || $input.val().parseDate(pattern);
			} else {
				return this.optional(element) || value.match(/^\d{4}[\/-]\d{1,2}[\/-]\d{1,2}$/);
			}
		}, "Please enter a valid date.");
		
		
		$.validator.addMethod("telephone", function(value, element) {
			return this.optional(element) || /^((\d{3,4}[\s\-])|(\d{3,4}))?\d{3,8}(([\s\-]\d{1,4})|(\d{1,4}))?$/.test(value);
		}, "Please enter a valid telephone number.");
		
		$.validator.addMethod("mobile", function(value, element) {
			return this.optional(element) || /^(13|15|18)\d{9}$/.test(value);
		}, "Please enter a valid mobile number.");
		
		$.validator.addMethod("postcode", function(value, element) {
			return this.optional(element) || /^[1-9]\d{5}$/.test(value);
		}, "Please enter a valid postcode number.");
		
		$.validator.addMethod("identitycard", function(value, element) {
			return this.optional(element) || /^(([1-9]\d{5}(19|20)\d{2}(([0][1-9])|([1][012]))(([0][1-9])|([12][0-9])|([3][01]))\d{3}([0-9]|[x]|[X]))|([1-9]\d{5}\d{2}(([0][1-9])|([1][012]))(([0][1-9])|([12][0-9])|([3][01]))\d{3}))$/.test(value);
		}, "Please enter a valid identitycard number.");
		
		$.validator.addMethod("bankcard", function(value, element) {
			return this.optional(element) || /^\d{16,}(\s)*$/.test(value);
		}, "Please enter a valid bankcard number.");
		
		$.validator.addMethod("pinyin", function(value, element) {
			return this.optional(element) || /^[A-Za-z0-9_][A-Za-z0-9_]+$/.test(value);
		}, "Please enter a valid pinyin.");
		
		$.validator.addMethod("multiEmail", function(value, element) {
			var valid = true;
			var emails = value.split(';');
			for ( var i = 0, length = emails.length; i < length; i++) {
				if (emails[i].length == 0 || emails[i].isValidMail()) {
					continue;
				} else {
					valid = false;
				}
			}
			return this.optional(element) || valid;
		}, "Please make sure that all the addresses are valid");
		
		$.validator.addClassRules({
			date: {date: true},
			alphanumeric: { alphanumeric: true },
			lettersonly: { lettersonly: true },
			phone: { phone: true },
			postcode1: {postcode1: true},
			telephone: {telephone: true},
			mobile: {mobile: true},
			postcode: {postcode: true},
			identitycard: {identitycard: true},
			bankcard: {bankcard: true},
			pinyin: {pinyin: true},
			multiEmail:{multiEmail:true}
		});
		$.validator.setDefaults({errorElement:"span"});
		$.validator.autoCreateRanges = false;
		
	}

})(jQuery);