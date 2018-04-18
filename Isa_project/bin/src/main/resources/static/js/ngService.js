var isaApp = angular.module('isaApp', ['ngRoute','ngMessages']);

isaApp.config(function($routeProvider) {
    $routeProvider
    .when("/", {
        templateUrl : "home.html"
    })
    .when("/register", {
        templateUrl : "register.html"
    })
    .when("/login", {
        templateUrl : "login.html"
    })
    .when("/confirmRegistration", {
        templateUrl : "confirmRegistration.html"
    })
    .when("/homePage", {
    	templateUrl : "homePage.html",
    })
    .when("/cinemas",{
    	templateUrl : "Cinemas.html"
    }).when("/funZone",{
    	templateUrl : "funZone.html"
    }).when("/reservation",{
    	templateUrl : "reservation.html"
    })
//    .when('/404', {
//    templateUrl: "modals/errorModal.html",
//})
});




isaApp.factory('isaService', function isaService($http){

	isaService.registerUser = function(user)
	{
		var data = {
			name: user.name,
			surname: user.surname,
			password: user.password,
			email: user.email,
			city: user.city,
			phone: user.phone,
//			userRole:"USER"
		};
		
		var config = {};
		return $http.post("user/register", user, config);
	}
	
	isaService.confirmRegistration = function(email)
	{
		console.log('service mail ' + email)
		var data = {email : email};
		var config = {};
		return $http.put("user/confirmRegistration/" + email, config);
	}
	
	isaService.getAllEmails = function()
	{
		return $http.get("user/getAllEmails");
	}
	
	isaService.login = function(user)
	{
		var data = user;
		var config = {};
		return $http.post("user/login", user, config);
	}
	
	return isaService;
});

