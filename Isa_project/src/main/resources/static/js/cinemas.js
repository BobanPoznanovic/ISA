
var app = angular.module('isaApp');

app.factory('CinemaTheaterService', function cinemaTheaterService($http) {
	cinemaTheaterService.getCinemas = function() {
		return $http({
			method : 'GET',
			url : 'isa/cinemaTheater/getCinemas'
		});
	}
	cinemaTheaterService.getTheaters = function() {
			return $http({
				method : 'GET',
				url : 'isa/cinemaTheater/getTheaters'
			});
		}
	cinemaTheaterService.registerTheater = function(theater) {
		return $http({
			method : 'POST',
			url : 'isa/cinemaTheater/registerTheater',
			data : {
				"name" : theater.name,
				"type" : 'THEATER',
				"description" : theater.description,
				"adress" : theater.adress,
				"rating" : 0
			}
		});
	}
	cinemaTheaterService.registerCinema = function(cinema) {
		return $http({
			method : 'POST',
			url : 'isa/cinemaTheater/registerCinema',
			data : {
				"name" : cinema.name,
				"type" : 'CINEMA',
				"description" : cinema.description,
				"adress" : cinema.adress,
				"city" : cinema.city,
				"rating" : 0
			}
		});
	}
	
	cinemaTheaterService.registerCinemaTheaterAdmin = function(cinema_id, user) {
		console.log('usaoooo');
		return $http({
			method : 'POST',
			url : 'user/registerCinemaTheaterAdmin?id=' + cinema_id,
			data : {
				"name" : user.name,
				"userRole" : "ADMIN_OF_CINEMA_THEATRE",
				"surname" : user.surname,
				"password" : user.password,
				"email" : user.email,
				"city" : user.city,
				"phone" : user.phone
			}
		});
	}
	
	cinemaTheaterService.getLoggedInUser = function() {
		return $http({
			method : 'GET',
			url : 'user/getLoggedInUser'
		});
	}
	
	return cinemaTheaterService;
});

app.controller(
		'CinemaTheaterController',
		[
				'$rootScope',
				'$scope',
				'$location',
				'$route',
				'CinemaTheaterService',
				function($rootScope, $scope, $location, $route,
						 cinemaTheaterService) {

					$scope.showView = function(number){
						$scope.show = number;
					}
					
					$scope.setSelected = function(selected) {
						$scope.selected = selected;
						$rootScope.cinema = $scope.selected;
						$scope.selectedCinemaAdmin = null;
						$scope.show = null;
						$scope.cinema = null;
						$scope.newCinemaAdmin = null;
						address = $scope.selected.adress;
						console.log($scope.selected.adress);
					}
					$scope.setSelected2 = function(selected) {
						$scope.selected = selected;
						$rootScope.theater = $scope.selected;
						$scope.selectedTheaterAdmin = null;
						$scope.show = null;
						$scope.theater = null;
						$scope.newTheaterAdmin = null;

					}
					cinemaTheaterService.getTheaters().then(function(response) {
						console.log('fja kontr');
						console.log(response.data);
						$scope.theaters = response.data;
						
					}, function myError(response) {
						
				    });
				
				$scope.registerTheater = function() {
					var theater = $scope.theater;
					cinemaTheaterService.registerTheater(
							theater).then(
							function(response) {
								console.log(response.data);
								if (response.data) {
									alert('Successfuly registrated theater')
									$scope.show = null;
									$route.reload();
								}
							}, function myError(response) {
								
						    });
				}
					
					cinemaTheaterService.getCinemas().then(function(response) {
							console.log('fja kontr');
							console.log(response.data);
							$scope.cinemas = response.data;
							
						}, function myError(response) {
							
					    });
					
					$scope.registerCinema = function() {
						var cinema = $scope.cinema;
						cinemaTheaterService.registerCinema(
								cinema).then(
								function(response) {
									console.log(response.data);
									if (response.data) {
										alert('Successfuly registrated cinema')
										$scope.show = null;
										$route.reload();
									}
								}, function myError(response) {
									
							    });
					}
					
					$scope.registerCinemaTheaterAdmin = function() {
						var newCinemaAdmin = $scope.newCinemaAdmin;
						console.log($scope.newCinemaAdmin);
						cinemaTheaterService.registerCinemaTheaterAdmin(
								$scope.selected.id,
								newCinemaAdmin).then(
								function(response) {
									if (response.data != null) {

										alert('Successfuly registrated cinema admin');

									} 
									$scope.show = null;
									$scope.newCinemaAdmin = null;
									$route.reload();
								}, function myError(response) {
						
								});
						
					}				
					
					$scope.verifyPassword = function(password, passwordCheck)
					{
						if(password != passwordCheck)
							$scope.noMatch = true;
						else if(password == passwordCheck)
							$scope.noMatch = false;
					}
					
					cinemaTheaterService.getLoggedInUser().then(function(response) {
						
						console.log(response.data);
						$scope.loginUserRole = 0;
						if(response.data.userRole == 'ADMIN'){
							$scope.loginUserRole = 1;
						}
						
					}, function myError(response) {
						
				    });
					
				}
				
				
				
		]);