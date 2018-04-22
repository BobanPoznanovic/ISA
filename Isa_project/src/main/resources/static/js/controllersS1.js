	var isaApp = angular.module('isaApp');


var passwordVerify = function() {
    return {
        require: "ngModel",
        scope: {
            otherModelValue: "=passwordVerify"
        },
        link: function(scope, element, attributes, ngModel) {
             
            ngModel.$validators.compareTo = function(modelValue) {
                return modelValue == scope.otherModelValue;
            };
 
            scope.$watch("otherModelValue", function() {
                ngModel.$validate();
            });
        }
    };
};


isaApp.directive('emailAvailable', ['isaService', function(isaService) {
	  return {
	    restrict: 'AE',
	    require: 'ngModel',
	    link: function (scope, elm, attrs, ctrl) {
            if (!ctrl) return;
            elm.bind('blur', function () {
                scope.$apply(checkUserName);
            });
            var checkUserName = function () {
                var email = elm.val();
                isaService.getAllEmails().then(function (result) {
                	for(var i = 0; i < result.data.length; i++)
                		
                    if (result.data[i] != email) {
                        ctrl.$setValidity('emailAvailable');
                    }
                });
                return email;
            };
        }
    };
}]);
	    

isaApp.directive("passwordVerify", passwordVerify);

isaApp.controller('ReservationController', ['$rootScope','$scope','$routeParams','$location','isaService', '$q', function RegistrationController($rootScope,$scope,$routeParams,$location,isaService){
	
	$scope.confirmReservation = function()
	{
		 var id = $routeParams.id;
		 
		 isaService.confirmReservation(id).then(function(response)
		 {
		 });
	}
	
	$scope.cancelReservationEmail = function()
	{
		 var id = $routeParams.id;
		 
		 isaService.cancelReservationEmail(id).then(function(response)
		 {
		 });
	}
	
}]);

isaApp.controller('RegistrationController', ['$rootScope','$scope','$routeParams','$location','isaService', '$q', function RegistrationController($rootScope,$scope,$routeParams,$location,isaService){
	
	$scope.phoneNumber = /^\+?\d{10}$/;
    $scope.email = /^[^\s@]+@[^\s@]+\.[^\s@]{2,}$/;
	$scope.duplicate = false;
	$scope.noMatch = false;
	$scope.confirmed = false;
	$scope.loggedInUser = "";
	
//	isaService.getLoggedInUser().then(function(response)
//	{
//		console.log(response.data);
//		
//	}, function myError(response) {
//		console.log('u er');
//		$location.path("/login");
//    });
	
    $scope.compare = function(password)
    {
    	if(password != $scope.passwordc)
    	{
    		$scope.registrationForm.passwordc.$error;
    	}
    }
     
	$scope.registerUser = function(user)
	{
		$scope.error = false;
		isaService.registerUser(user).then(function(response)
		{
			
			if(response.data){
				$scope.error = false;
			}else{
			}
		});
		
	}

	$scope.confirmRegistration = function()
	{
		 var email = $routeParams.email;
		 
		 isaService.confirmRegistration(email).then(function(response)
		 {
		 });
	}
	
	$scope.verifyDuplicate = function(email)
	{
		isaService.getAllEmails().then(function(response)
		{
			var allEmail = response.data;
			for(var i = 0; i < allEmail.length; i++)
			{
				if(allEmail[i].email.match(email))
				{	
					$scope.duplicate = true;
					break;
				}else
					$scope.duplicate = false;
			}
			
		});
	}
	
	$scope.verifyPassword = function(password, passwordCheck)
	{
		if(password != passwordCheck)
			$scope.noMatch = true;
		else if(password == passwordCheck)
			$scope.noMatch = false;
	}
	
	$scope.login = function(user)
	{
		
		isaService.login(user).then(function(response)
		{
			$scope.loggedInUser = response.data;
			
			if($scope.loggedInUser.name == null)
			{
				alert("You have to register first");
				$location.path("/register");
			}
			
			if($scope.loggedInUser.status == "NOT_ACTIVATED")
			{	
				alert("Account not acctivated");
				$location.path("/login");
			}else if($scope.loggedInUser.status == "ACTIVATED")
			{
				//alert("Welcome " + $scope.loggedInUser.name);
//				console.log($scope.loggedInUser);
				if($scope.loggedInUser.firstLogIn == false && $scope.loggedInUser.userRole == 'ADMIN_OF_FAN_ZONE'){
					console.log($scope.loggedInUser.firstLogIn);
					console.log($scope.loggedInUser.userRole);
					$location.path("/changePassword");
				}else{
				
				//ovde menjamo logiku gde baca usera
					if($scope.loggedInUser.userRole == 'ADMIN') {
						
						$location.path("/systemAdmin");
					}
					else {
						$location.path("/homePage");
					}
					
				}
			}
			
			
		},
		function(data) {
	        // Handle error here
			alert("Invalid password");
			$location.path("/login");
		});
	}
	
}]);

isaApp.directive('starRating', function () {
    return {
        restrict: 'A',
        template: '<ul class="rating">' +
            '<li ng-repeat="star in stars" ng-class="star">' +
            '\u2605' +
            '</li>' +
            '</ul>',
        scope: {
            ratingValue: '=',
            max: '='
        },
        link: function (scope, elem, attrs) {
            scope.stars = [];
            for (var i = 0; i < scope.max; i++) {
                scope.stars.push({
                    filled: i < scope.ratingValue
                });
            }
        }
    }
});


isaApp.controller('HomePageController', ['$rootScope','$scope','$routeParams','$location','isaService','$q', function HomePageController($rootScope,$scope,$routeParams,$location,isaService){

//	LOGIN AND LOGOUT	
	isaService.getLoggedInUser().then(function(response)
	{ 
		$rootScope.loggedIn = response.data;
		$scope.loggedIn = response.data;
		$scope.editUser = $scope.loggedIn;		// da bi inicijalno ispisao podatke u settings
		$scope.editUser.password = "";
		
		if($rootScope.loggedIn.status == "NOT_ACTIVATED")
			$location.path("/login");
		
	}, function myError(response) {
		$location.path("/login");
    });
	
	
	isaService.getWatchedReservations().then(function(response)
	{
		$scope.watchedReservations = response.data;
	});
		
	
	$scope.logout = function()
	{
		isaService.logout().then(function(response)
		{
			$location.path("/");
		});
	}
	
	$scope.addNewAdmin = function()
	{
		isaService.addNewAdmin().then(function(response){
			$location.path("")
		});
	}
	
	
	$scope.getClass = function (path) {
		  return ($location.path().substr(0, path.length) === path) ? 'active' : '';
		}
	
	$scope.lastSearch = "";
	
	$scope.searchForFriends = function(friend)
	{
		$scope.friends = [];
		$rootScope.recieverId = [];
		isaService.searchForFriends(friend).then(function(response)
		{
			$scope.friends = response.data;
		});
		
		$scope.lastSearch = "friends";
	}
	
	// potrebno zbog fronta i razlicite reprezentacije statusa 
	$scope.searchFriendship = function(result)
	{
		isaService.searchFriendship().then(function(response)
		{
			var nasao = false;
			if(result.id != undefined)
			{	
				if(response.data.length == 0)
				{
					result.Action = 'notSent';
				}
				
				if(!nasao)
				for(var i = 0; i < response.data.length; i++)
				{
					if(response.data[i].sender.id == $rootScope.loggedIn.id && response.data[i].reciever.id == result.id)
					{						
						if(response.data[i].status === 'WAITING')
						{	
								result.Action = 'sent';
								nasao = true;
								
						}else if(response.data[i].status == 'ACCEPTED')
						{
							result.Action = 'friends';
							nasao = true;
						}
						
					}else if(response.data[i].reciever.id == $rootScope.loggedIn.id && response.data[i].sender.id == result.id)
					{
						if(response.data[i].status === 'WAITING')
						{	
								result.Action = 'confirm';
								nasao = true;
								
						}else if(response.data[i].status == 'ACCEPTED')
						{
							result.Action = 'friends';
							nasao = true;
						}
					}
					
					if(nasao)
					{
						break;
					}else
					{
						result.Action = 'notSent';
					}
				}
			}
		},
		
		function(data) {
			console.log("vec postoji friendship");
		});
		$scope.lastSearch = "friends";
	}
	
	$scope.updateUser = function()
	{
		isaService.updateUser($scope.editUser).then(function(response)
		{
			$scope.editUser = response.data;
			$scope.editUser.password = "";
		});
		
	}
	
	$scope.resetUser = function()
	{
//		$scope.editUser = $rootScope.loggedIn;
	}
	
	$scope.numberView = 0;		// inicijalno na home
	$scope.reserveTypes = [];
	$scope.show = function(num)
	{
		$scope.numberView = num;
		$scope.lastSearch = "";
		$scope.step = 0;
		
		if($scope.numberView == 3)
			$scope.tabView = 0;
		
		if($scope.numberView == 4)
		{	
			$scope.tabView = 3;
			$scope.reserveTypes = [{
			    value: 'cinema',
			    label: 'cinema'
			  },{
				  value: 'theater',
				  label: 'theater'
			  }];
		if($scope.tabView == 4)
			$scope.step = 1;
		}
		
		if($scope.tabView != 4)
		{
			$scope.all = {};
			$scope.allProjections = {};
			$scope.dates = {};
			$scope.times = {};
			$scope.halls = {};
			$scope.invitedFriends = [];
			
		}
		
		if($scope.numberView == 11)
		{	
			
			$scope.tabView = 3;
			$scope.reserveTypes = [{
			    value: 'cinema',
			    label: 'cinema'
			  },{
				  value: 'theater',
				  label: 'theater'
			  }];
		if($scope.tabView == 4)
			$scope.step = 1;
		}
	}

//	VALIDATION
	$scope.verifyDuplicate = function(email)
	{
		isaService.getAllEmails().then(function(response)
		{
			var allEmail = response.data;
			for(var i = 0; i < allEmail.length; i++)
			{
				if(allEmail[i].email.match(email))
				{	
					$scope.duplicate = true;
					break;
				}else
					$scope.duplicate = false;
			}
			
		});
	}
	
	$scope.verifyPassword = function(password, passwordCheck)
	{
		if(password != passwordCheck)
			$scope.noMatch = true;
		else if(password == passwordCheck)
			$scope.noMatch = false;
	}
//	VALIDATION

	$scope.requestSent = false;
	$scope.sendRequest = function(reciever)
	{
		var sender = $rootScope.loggedIn;
		isaService.sendRequest(reciever).then(function(response)
		{
			$scope.sent = response.data;	
		},
		
		function(data) {
	        // Handle error here
			console.log("vec postoji friendship");
		});
	}
	
	$scope.clickFunc = function(item)
	{
		  item.Action = "sent";
	}
	
	$scope.clickFuncConfirm = function(item)
	{
		  item.Action = "friends";
	}
	
	$scope.setSelected = function(selected) 
	{
		$scope.selected = selected;
	}
	
	$scope.tabView = 0;		// inicijalno na home
	$scope.showTab = function(num)
	{
		$scope.tabView = num;
		$scope.lastSearch = "";
		
		if($scope.tabView == 4)
			$scope.step = 1;
		
		if($scope.tabView != 4)
		{
			$scope.all = {};
			$scope.allProjections = {};
			$scope.dates = {};
			$scope.times = {};
			$scope.halls = {};
			$scope.invitedFriends = [];
			
		}
	}
	isaApp
	$scope.friendRequests = function(){
		
		isaService.friendRequests().then(function(response)
		{
			$scope.friendRequest = response.data;
			for(var i = 0; i > $scope.friendRequest.length; i++)
				console.log($scope.friendRequest[i].name);
			
			if($scope.friendRequest.length == 0)
				$scope.noRequest = true;
		});
	}
		
	$scope.confirmRequest = function(sender)
	{
		$scope.status = "waiting";
		isaService.confirmRequest(sender).then(function(response)
		{
			if(response.data.status == "ACCEPTED")
			{	$scope.status = "accepted";
				alert("Friend request accepted");
//				$scope.friendRequest.splice($scope.friendRequest.indexOf(sender), 1);
//				$scope.friends.splice($scope.friends.indexOf(sender), 1);
				sender.Action = 'friends'
//				$scope.tabView = 1;
			}
		});
	}
	
	
	$scope.getFriendsList = function()
	{
		isaService.getFriendsList().then(function(response)
		{
			$scope.friendsList = response.data.sort();
		});
		
	}
	
	
	$scope.deleteFriendship = function(friend)
	{
		isaService.deleteFriendship(friend.id).then(function(response)
		{
			$scope.friendsList.splice($scope.friendsList.indexOf(friend), 1);
//			$scope.friendRequest.splice($scope.friendRequest.indexOf(friend), 1);
//			$scope.friends.splice($scope.friends.indexOf(friend), 1);
			friend.Action = 'notSent';
		});
	}
	
	
	
//	SEARCH
	$scope.searchCinemas = function(cinema)
	{
		isaService.searchCinemas(cinema).then(function(response)
		{
			$scope.cinemas = response.data.sort();
			
			for(var i = 0; i < $scope.cinemas.length; i++)
			{
				$scope.ratings = [{
			        current: $scope.cinemas[i].rating,
			        max: 5
			    
			    }];
			}
			
		});
		
		$scope.lastSearch = "cinemas";
	}
	
	$scope.searchTheatres = function(theater)
	{
		isaService.searchTheaters(theater).then(function(response)
		{
			$scope.theaters = response.data.sort();
		});
		
		$scope.lastSearch = "theaters";
	}
	$scope.reserve = function(cinema)
	{
		$scope.numberView = 4;
		$scope.lastSearch = "reservation";
		$scope.step = 1;
	}
	
//	RESERVATION
	
	$scope.step = 0;
	var i = 1;
	$scope.nextStep = function() 
	{
		$scope.step = ++$scope.step;
	}
	
	$scope.previousStep = function()
	{
		$scope.step = --$scope.step;
	}
	
	
	resetValues = function()
	{
		$scope.dates = {};
		$scope.times = {};
		$scope.halls = {};
	}
	
	$scope.typeChanged = function()
	{
		if($scope.reserveTypes.value == 'cinema')
		{
			isaService.getCinemas().then(function(response)
			{
				$scope.all = response.data.sort();
				console.log($scope.all);
			});
			
		}else if($scope.reserveTypes.value == 'theater')
		{	
			isaService.getTheaters().then(function(response)
			{
				$scope.all = response.data.sort();
			});
		}
	}
	
	$scope.getProjections = function(id)
	{
		isaService.getProjections(id).then(function(response)
		{
			$scope.allProjections = response.data.sort();
		});
	}
	

	$scope.searchProj = function(th)
	{
		console.log(th);
		isaService.getProjections(th.id).then(function(response)
		{
			$scope.thProjections = response.data.sort();
		});
		
		$scope.showModal = true;
		$scope.lastSearch = "projections";
	}
	
	
	$scope.ctNameChanged = function()
	{
		isaService.getProjections($scope.all.id).then(function(response)
		{
			$scope.allProjections = response.data.sort();
		});
		
//		resetValues();
		$scope.dates = {};
		$scope.times = {};
		$scope.halls = {};
	}
	
	$scope.projectionChanged = function()
	{
		isaService.getDates($scope.allProjections.id).then(function(response)
		{
				$scope.dates = response.data;
		});
		
		$scope.times = {};
		$scope.halls = {};
		
	}
	
	$scope.dateChanged = function()
	{
		$scope.times = [];
		$scope.halls = [];
		$scope.selTime = {};
		var number = 0;
		
		isaService.getTimes($scope.allProjections.id, $scope.dates.id).then(function(response)
		{
			$scope.times= response.data;
		});
		
		$scope.halls = {};
	}
	
	$scope.timeChanged = function()
	{
		isaService.getHalls($scope.allProjections.id, $scope.dates.id, $scope.times.id).then(function(response)
		{
			$scope.halls = response.data.sort();
		});
	}
	
	$scope.getRowsColumns = function()
	{
		$scope.rows = [];
		$scope.columns = [];
		
		isaService.getRowsColumns($scope.halls.id).then(function(response)
		{
			$scope.seatNumber = 0;
			$scope.rowsLength = parseInt(response.data[0]);
			$scope.columnsLength = parseInt(response.data[1]);
			
			var i = 0;
			
			for(var i = 0; i < $scope.rowsLength; i++)
			{
				newItem = {title: i + 1, selected: false};
				$scope.rows.push(newItem);
			}
			
			for(var i = 0; i < $scope.columnsLength; i++)
			{	
				$scope.seatNumber++;
				newItem = {title: i + 1, selected: false};
				$scope.columns.push(newItem);
			}
		});
		
		$scope.matrix = {};
		$scope.itemsMap = {};
		
		isaService.getSeats($scope.allProjections.id, $scope.dates.id, $scope.times.id, $scope.halls.id).then(function(response)
		{
			$scope.seats = response.data;
			$scope.matrix = $scope.seats.freeSeats;
			
		});
		
		$scope.getTakenSeats();
		
//		$scope.matrix = $scope.free;
	}
	
	$scope.getTakenSeats = function()
	{
		isaService.getSeats($scope.allProjections.id, $scope.dates.id, $scope.times.id, $scope.halls.id).then(function(response)
			{
				$scope.renderMatrix = response.data.freeSeats;
			});
	}
	
	$scope.hallChanged = function()
	{
		$scope.matrix = {};
		console.log(halls.id);
			isaService.getSeats($scope.allProjections.id, $scope.dates.id, $scope.times.id, $scope.halls.id).then(function(response)
			{
				$scope.seats = response.data;
				$scope.matrix = $scope.seats.freeSeats;
			 });
	}
	
	
	$scope.makeReservation = function()
	{
		$scope.reservation = 
		{
				name: $scope.all.id,
				projection: $scope.allProjections.id,
				date: $scope.dates.id,
				time: $scope.times.id,
				hall: $scope.halls.id,
				seats: $scope.reservationMatrix,
				friends: $scope.invitedFriends
				
		};
		
		isaService.makeReservation($scope.reservation).then(function(response)
		{
			if(response.data == "The number of additionaly chosen seats must be equal to the number of selecte friends.")
			{
				alert("The number of additionaly chosen seats must be equal to the number of selected friends.");
				$scope.invitedFriends = [];
				
			}else if(response.data == "One of your friends already has a reservation for this term.")
			{
				alert("One of your friends already has a reservation for this term.");
				
			}else if(response.data == "You can choose a maximum of 5 seats.")
			{
				alert("You can choose a maximum of 5 seats.");
				
			}else if(response.data == "You already have a reservation for this term.")
			{
				alert("You already have a reservation for this term.");
				
			}else if(response.data == "You have to choose at least one seat.")
			{
				alert("You have to choose at least one seat.");
				
			}else if(response.data == "Reservation has been successfuly made.")
			{
				alert("Reservation has been successfuly made.");
				$scope.tabView = 3;
				
			}else if(response.data == "Someone has already reserved those seats")
			{
				alert("Someone has already reserved those seats.");
				
			}else if("You have selected an inadequate number of seats or some of the seats got reserved in the mean time")
			{
				alert("You have selected an inadequate number of seats or some of the seats got reserved in the mean time.");
			}
		});
	}
	
	
	$scope.showChanged = function()
	{
		$scope.reservationMatrix = $scope.matrix;
	}

	
	$scope.invitedFriends = [];
	$scope.inviteFriend = function(friend)
	{
		$scope.invitedFriends.push(friend);
	}
	
	
	$scope.getValues = function()
	{
		console.log($scope.allProjections.id);
		isaService.getProjectionsName($scope.allProjections.id).then(function(response)
		{
			$scope.projectionName = response.data;
			console.log("aaaa" + $scope.projectionName);
		});
	}
	
	
	$scope.getReservations = function()
	{
		isaService.getReservations().then(function(response)
		{
			$scope.reservationList = response.data.sort();
		});
	}
	
	
	$scope.getFriends = function()
	{
		isaService.getFriendsList().then(function(response)
		{
			$scope.friends = response.data.sort();
			
			if($scope.invitedFriends.length == 0)
			{
				for(var i = 0; i < $scope.friends.length; i++)
				{
					$scope.friends[i].sent = 'notSent';
				}
			}else
			{
				for(var i = 0; i < $scope.friends.length; i++)
				{
					$scope.friends[i].sent = 'notSent';
				}
				
				for(var i = 0; i < $scope.friends.length; i++)
				{
					for(var j = 0; j < $scope.invitedFriends.length; j++)
					{
						if($scope.friends[i].id === $scope.invitedFriends[j].id)
							$scope.friends[i].sent = 'sent';
							
					}
				}
			}
		});
	}
	
	$scope.clickSent = function(friend)
	{
		friend.sent = 'sent';
	}
	
	$scope.cancelReservation = function(reservation)
	{
		isaService.cancelReservation(reservation.id).then(function(response)
		{
			$scope.reservationList.splice($scope.reservationList.indexOf(reservation), 1);
//			$scope.friendRequest.splice($scope.friendRequest.indexOf(friend), 1);
//			$scope.friends.splice($scope.friends.indexOf(friend), 1);
			reservation.Action = 'notSent';
		});
	}

}]);
