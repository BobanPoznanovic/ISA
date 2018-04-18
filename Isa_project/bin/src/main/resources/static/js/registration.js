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
	          
	        // return the promise of the asynchronous validator
	      
	    	
//	    	  var email = elm.val();
//	    	  console.log(email);
//	    	 isaService.getAllEmails().then(function(response)
//				{
//					var allEmail = response.data;
//					console.log(allEmail)
//					for(var i = 0; i < allEmail.length; i++)
//					{
//						if(allEmail.email == email)
//						{
//							$scope.duplicate = true;
//							console.log('nije duplikat');
//							ngModel.$setValidity('usernameExists', false); 
//						}else
//							ngModel.$setValidity('usernameExists', true);
//					}
//				});
	        //here you should access the backend, to check if username exists
	        //and return a promise
	        //here we're using $q and $timeout to mimic a backend call 
	        //that will resolve after 1 sec

	  
	     

isaApp.directive("passwordVerify", passwordVerify);

isaApp.controller('RegistrationController', ['$rootScope','$scope','$routeParams','$location','isaService', '$q', function RegistrationController($rootScope,$scope,$routeParams,$location,isaService){
	
	$scope.phoneNumber = /^\+?\d{10}$/;
    $scope.email = /^[^\s@]+@[^\s@]+\.[^\s@]{2,}$/;
	$scope.duplicate = false;
	$scope.noMatch = false;
	$scope.confirmed = false;
	$scope.loggedInUser = "";
//	$scope.statusFail = false;
	
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
////				$location.path("/");
			}else{
//				$scope.error = true;
			}
////				user = response.data;
				console.log('aaaaaaaaaaaaaaa ' + response.data);
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
			console.log($scope.loggedInUser);
			
			if($scope.loggedInUser.status == "NOT_ACTIVATED")
			{	
				alert("Account not acctivated");
				$location.path("/login");
			}else if($scope.loggedInUser.status == "ACTIVATED")
			{
				alert("Welcome " + $scope.loggedInUser.name);
				$location.path("/homePage");
			}
		},
		function(data) {
	        // Handle error here
			console.log("u erroru asm");
			alert("Invalid username or password");
			$location.path("/login");
		});
	}
	
	
}]);


