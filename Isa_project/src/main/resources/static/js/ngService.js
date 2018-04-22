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
    .when("/systemAdmin", {
        templateUrl : "systemAdmin.html"
    })
    .when("/homePage", {
    	templateUrl : "../userPages/homePage.html",
    })
    .when("/cinemas",{
    	templateUrl : "Cinemas.html"
    		
    })
    .when("/theaters",{
    	templateUrl : "Theaters.html"
    
    }).when("/funZone",{
    	templateUrl : "funZone.html"
    		
    }).when("/confirmReservation", {
    	templateUrl : "confirmReservation.html"
    		
    }).when("/cancel", {
    
        templateUrl : "cancelReservation.html"
        
    }).when("/profil",{
    	templateUrl : "profil.html"
    	
    }).when("/changePassword",{
    	templateUrl : "changePassword.html"
    
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
	
	isaService.confirmReservation = function(id)
	{
		var data = {id : id};
		var config = {};
		return $http.put("reservation/confirmReservation/" + id, config);
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
	
	isaService.cancelReservation = function(id)
	{
		return $http.delete("reservation/cancelReservation/" + id);
	}

	isaService.cancelReservationEmail = function(id)
	{
		var data = {id : id};
		var config = {};
		return $http.put("reservation/cancel/" + id, config);
	}
	
	isaService.logout = function()
	{
		return $http.get("user/logout");
	}
	
	isaService.searchCinemas = function(cinema)
	{
		var name = "";
		var adress = "";
//		var city = "";
		
		if(cinema === undefined)
		{
			name = "";
			adress = "";
			city = "";
			
		}else{
			
			if(cinema.name == null || cinema.name == undefined || cinema.name == "")
				name = " ";
			else
				name = cinema.name;
			if(cinema.adress == null || cinema.adress == undefined || cinema.adress == "")
				adress = " ";
			else
				adress = cinema.adress;
//			if(cinema.city == null || cinema.city == undefined || cinema.city == "")
//				city = " ";
//			else
//				city = cinema.city;
		}
		
		var config = 
		{
				params:
				{
					name: name,
					adress: adress
//					city: city
				}
		};
		
		return $http.get("isa/cinemaTheater/searchCinemas", config);
	}
	
	isaService.searchTheaters = function(theater)
	{
		var name = "";
		var adress = "";
//		var city = "";
		
		if(theater === undefined)
		{
			name = "";
			adress = "";
			
		}else{
			
			if(theater.name == null || theater.name == undefined || theater.name == "")
				name = " ";
			else
				name = theater.name;
			if(theater.adress == null || theater.adress == undefined || theater.adress == "")
				adress = " ";
			else
				adress = theater.adress;
		}
		
		var config = 
		{
				params:
				{
					name: name,
					adress: adress
//					city: city
				}
		};
		
		return $http.get("isa/cinemaTheater/searchTheaters", config);
	}
	
	isaService.getCinemas = function() {
		return $http({
			method : 'GET',
			url : 'isa/cinemaTheater/getCinemas'
		});
	}
	
	isaService.getTheaters = function() {
		return $http({
			method : 'GET',
			url : 'isa/cinemaTheater/getTheaters'
		});
	}
	
	isaService.getProjections = function(id) {
		var config = 
		{
				params:
				{
					id: id
				}
		};
		return $http.get("projection/getProjections", config);
	}
	
	isaService.getDates = function(id)
	{
		var config = 
		{
				params:
				{
					id: id
				}
		};
		return $http.get("term/getDates", config);
	}
	
	isaService.getTerms = function(projectionId, date)
	{
		var config = 
		{
				params:
				{
					projectionId: projectionId,
					termDate: date
				}
		};
		return $http.get("term/getTerms", config);
	}

	isaService.getTimes = function(projectionId, date)
	{
		var config = 
		{
				params:
				{
					projectionId: projectionId,
					termDate: date
				}
		};
		return $http.get("term/getTimes", config);
	}
	
	isaService.getHalls = function(projectionId, date, time)
	{
		var config = 
		{
				params:
				{
					projectionId: projectionId,
					termDate: date,
					termTime: time
				}
		};
		return $http.get("term/getHalls", config);
	}
	
	isaService.getRowsColumns = function(hallId)
	{
		var config = 
		{
				params:
				{
					id: hallId
				}
		};
		return $http.get("hall/getRowsColumns", config);
	}
	
	isaService.getSeats = function(pr, date, time, hall)
	{
		var config = 
		{
				params:
				{
					projId: pr,
					termDate: date,
					termTime: time,
					hallId: hall
				}
		};
		return $http.get("term/getSeats", config);
	}
	
	isaService.makeReservation = function(reservation)
	{
		var config = {};
		return $http.put("reservation/reserve", reservation, config);
	}
	
	isaService.getProjectionsName = function(id)
	{
		console.log(id);
		var config = {
				params: {id :id}
		};
		return $http.get("projection/getNames", config);
	}
	
	isaService.getReservations = function()
	{
		var config = {};
		return $http.get("reservation/getReservations", config);
	}
	
	isaService.getWatchedReservations = function()
	{
		var config = {};
		return $http.get("reservation/getWatchedReservations", config);
	}
	
	return isaService;
});

