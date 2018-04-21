
var app = angular.module('isaApp');

app.factory('FunZoneService', function funZoneService($http) {
	funZoneService.getUsedApprovedAds = function() {
		return $http({
			method : 'GET',
			url : 'funZone/getUsedApprovedAds'
		});
	}
	
	
	funZoneService.createAd = function(ad) {
		return $http({
			method : 'POST',
			url : 'funZone/createAd',
			data : {
				"name" : ad.name,
				"type" : 'USED',
				"description" : ad.description,
				"valid_to" : ad.date,
				"photo" : 'nistaa'
			}
		});
	}
	
	return funZoneService;
});

app.controller(
		'FunZoneController',
		[
				'$rootScope',
				'$scope',
				'$location',
				'$route',
				'FunZoneService',
				function($rootScope, $scope, $location, $route,
						 funZoneService) {
					
					$scope.showView = function(number){
						$scope.show = number;
					}
					
					$scope.showUsedProps = function(number){
						$scope.showUsedPropsNum = number;
					}
					
					$scope.setSelected = function(selected) {
						$scope.selected = selected;
					}
					
					$scope.createAd = function() {
						var ad = $scope.ad;
						console.log(ad)
						funZoneService.createAd(
								ad).then(
								function(response) {
									console.log(response.data);
									if (response.data) {
										alert('Successfuly create ad')
										$scope.showUsedProps = null;
									}
								}, function myError(response) {
									
							    });
					}
					
					funZoneService.getUsedApprovedAds().then(function(response) {

						$scope.ads = response.data;
						
					}, function myError(response) {
						
				    });
				
				}
		]);