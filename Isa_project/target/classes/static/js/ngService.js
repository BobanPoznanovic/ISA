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
    	templateUrl : "../userPages/homePage.html",
    })
    .when("/cinemas",{
    	templateUrl : "Cinemas.html"
    }).when("/funZone",{
    	templateUrl : "funZone.html"
    })
});

isaApp.factory('isaService', function isaService($http){

	isaService.getLoggedInUser = function()
	{
		return $http.get("user/getLoggedInUser");
	}
	
	isaService.registerUser = function(user)
	{
		var data = {
			name: user.name,
			surname: user.surname,
			password: user.password,
			email: user.email,
			city: user.city,
			phone: user.phone,
		};
		
		var config = {};
		return $http.post("user/register", user, config);
	}
	
	isaService.confirmRegistration = function(email)
	{
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
	
	isaService.searchForFriends = function(friend)
	{
		if(friend === undefined)
		{
			name = "";
			surname = "";
		}
		
		var name = "";
		if(friend != undefined)
		{
			if(friend.name === undefined || friend.name === "" || friend.name === null)
				name = " ";
			else
				name = friend.name;
		
			var surname = "";
			if(friend.surname === undefined || friend.surname === "" || friend.surname === null)
				surname = " ";
			else
				surname = friend.surname;
		}
		
		var config = 
		{
				params:
				{
					name: name,
					surname: surname
				}
		};
		
		return $http.get("user/searchForFriends", config);		
	}
	
	isaService.updateUser = function(user)
	{
		return $http.put("user/updateUser/" + user.id, user);
	}
	
	isaService.sendRequest = function(reciever)
	{
		var config = {};
		return $http.post("friendship/sendRequest", reciever);
	}
	
	isaService.searchFriendship = function()
	{
		return $http.get("friendship/getFriendshipsStatus");
	}
	
	isaService.friendRequests = function()
	{
		return $http.get("friendship/getFriendRequests");
	}
	
	isaService.confirmRequest = function(sender)
	{
		return $http.put("friendship/confirmRequest/" + sender.id, sender);
	}
	
	isaService.getFriendsList = function()
	{
		return $http.get("friendship/getFriendsList");
	}
	
	isaService.deleteFriendship = function(senderId)
	{
		return $http.delete("friendship/deleteFriendship/" + senderId);
	}
	
	isaService.logout = function()
	{
		return $http.get("user/logout");
	}
	
	return isaService;
});

