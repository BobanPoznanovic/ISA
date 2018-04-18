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
                	console.log(result.data);
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
    		console.log(password);
    		$scope.registrationForm.passwordc.$error;
    	}
    }
     
	$scope.registerUser = function(user)
	{
		/*if (!$rootScope.loggedUser) {
			$location.path('/register');
		} else {
			$location.path('/shoppingCart');
		}
		
		console.log(user);*/
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
		 console.log(email);
		 
		 isaService.confirmRegistration(email).then(function(response)
		 {
			/* console.log('dataa ' + response.data);
			 if(response.data == 'confirmed')
				 confirmed = true;
			 else
				 confirmed = false;*/
		 });
		 console.log('vratio ');
	}
	
	$scope.verifyDuplicate = function(email)
	{
		isaService.getAllEmails().then(function(response)
		{
			var allEmail = response.data;
			console.log('1' +$scope.duplicate);
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
				alert("Welcome " + $scope.loggedInUser.name);
//				console.log($scope.loggedInUser);
				$location.path("/homePage");
			}
		},
		function(data) {
	        // Handle error here
			console.log("u erroru asm");
			alert("Invalid password");
			$location.path("/login");
		});
	}
	
}]);

isaApp.controller('HomePageController', ['$rootScope','$scope','$routeParams','$location','isaService', '$q', function HomePageController($rootScope,$scope,$routeParams,$location,isaService){

	isaService.getLoggedInUser().then(function(response)
	{ 
		$rootScope.loggedIn = response.data;
		$scope.loggedIn = response.data;
//		console.log($rootScope.loggedIn);
		$scope.editUser = $scope.loggedIn;		// da bi inicijalno ispisao podatke u settings
		$scope.editUser.password = "";
		
		if($rootScope.loggedIn.status == "NOT_ACTIVATED")
			$location.path("/login");
		
	}, function myError(response) {
		$location.path("/login");
    });
	
//	$scope.getLoggedIn = function()
//	{
//		isaService.getLoggedInUser().then(function(response)
//		{
//			$scope.loggedIn = response.data;
//			console.log($rootScope.loggedIn);
//			$scope.editUser = $scope.loggedIn;		// da bi inicijalno ispisao podatke u settings
//			$scope.editUser.password = "";
//
//		});
//	}
	
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
//			console.log($scope.friends);
			
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
//		$scope.tmpUser = JSON.parse(JSON.stringify($scope.editUser));
//		console.log($scope.tmpUser);
		isaService.updateUser($scope.editUser).then(function(response)
		{
			$scope.editUser = response.data;
			console.log(response.data);
//			$rootScope.loggedIn = response.data;
			$scope.editUser.password = "";
		});
		
	}
	
	$scope.resetUser = function()
	{
//		$scope.editUser = $rootScope.loggedIn;
	}
	
	$scope.numberView = 0;		// inicijalno na home
	$scope.show = function(num)
	{
		$scope.numberView = num;
		$scope.lastSearch = "";
		if($scope.numberView == 3)
			$scope.tabView = 0;
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
	
	$scope.setSelected = function(selected) {
		$scope.selected = selected;
	}
	
	$scope.tabView = 0;		// inicijalno na home
	$scope.showTab = function(num)
	{
		$scope.tabView = num;
		$scope.lastSearch = "";
	}
	
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
				$scope.friendRequest.splice($scope.friendRequest.indexOf(sender), 1);
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
			$scope.friendsList = response.data;
			for(var i = 0; i < response.data.length; i++)
			{
				$scope.friendsList = response.data.sort();
			}
			
		});
	}
	
	$scope.deleteFriendship = function(friend)
	{
		isaService.deleteFriendship(friend.id).then(function(response)
		{
			$scope.friendsList.splice($scope.friendsList.indexOf(friend), 1);
			$scope.friendRequest.splice($scope.friendRequest.indexOf(friend), 1);
//			$scope.friends.splice($scope.friends.indexOf(friend), 1);
			friend.Action = 'notSent';
		});
	}
	
	$scope.logout = function()
	{
		isaService.logout().then(function(response)
		{
			$location.path("/");
		});
	}
	
}]);
