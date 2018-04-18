var app = angular.module('isaApp');

app.factory('CinemaTheaterService', function cinemaTheaterService($http) {
	cinemaTheaterService.getCinemas = function() {
		return $http({
			method : 'GET',
			url : 'isa/cinemaTheater/getCinemas'
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
					
				}
				
				
				
		]);