
var photo;

var app = angular.module('isaApp');

app.factory('FunZoneService', function funZoneService($http) {
	funZoneService.getUsedApprovedAds = function() {
		return $http({
			method : 'GET',
			url : 'funZone/getUsedApprovedAds'
		});
	}
	
	funZoneService.getOfficialAds = function() {
		return $http({
			method : 'GET',
			url : 'funZone/getOfficialAds'
		});
	}
	
	funZoneService.getAdsForApproved = function() {
		return $http({
			method : 'GET',
			url : 'funZone/getAdsForApproved'
		});
	}
	
	funZoneService.getLoggedInUser = function() {
		return $http({
			method : 'GET',
			url : 'user/getLoggedInUser'
		});
	}
	
	funZoneService.getCreditRating = function() {
		return $http({
			method : 'GET',
			url : 'funZone/getCreditRating'
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
				"photo" : ad.photo
			}
		});
	}
	
	funZoneService.createOfficialAd = function(ad) {
		console.log(ad.description);
		return $http({
			method : 'POST',
			url : 'funZone/createOfficialAd',
			data : {
				"name" : ad.name,
				"type" : 'OFFICIAL',
				"description" : ad.description,
				"valid_to" : null,
				"photo" : 'nistaa',
				"price" : ad.price,
				"approved" : null
			}
		});
	}
	
	funZoneService.sendOffer = function(offer, ad) {
		return $http({
			method : 'POST',
			url : 'funZone/createOffer/' + ad + '/' + offer,
		});
	}
	
	funZoneService.getOffersOfAd = function(ad) {
		return $http({
			method : 'POST',
			url : 'funZone/getOffersOfAd/' + ad,
		});
	}
	
	funZoneService.acceptOffer = function(ad) {
		return $http({
			method : 'POST',
			url : 'funZone/acceptOffer/' + ad,
		});
	}
	
	funZoneService.approvedAd = function(ad) {
		
		return $http({
			method : 'POST',
			url : 'funZone/approvedAd',
			data : {
				"id" : ad.id,
				"name" : ad.name,
				"type" : ad.type,
				"description" : ad.description,
				"valid_to" : ad.valid_to,
				"photo" : 'nistaa'
			}
		});
	}
	
	funZoneService.rejectedAd = function(ad) {
		
		return $http({
			method : 'POST',
			url : 'funZone/rejectedAd',
			data : {
				"id" : ad.id,
				"name" : ad.name,
				"type" : ad.type,
				"description" : ad.description,
				"valid_to" : ad.valid_to,
				"photo" : 'nistaa'
			}
		});
	}
	
	funZoneService.buyProp = function(ad) {
		console.log(ad);
		return $http({
			method : 'POST',
			url : 'funZone/buyProp',
			data : {
				"id" : ad.id,
				"name" : ad.name,
				"type" : ad.type,
				"description" : ad.description,
				"valid_to" : ad.valid_to,
				"photo" : 'nistaa'
			}
		});
	}
	
	funZoneService.changeAd = function(ad) {
		console.log(ad);

		return $http({
			method : 'POST',
			url : 'funZone/changeAd',
			data : {
				"id" : ad.id,
				"name" : ad.name,
				"type" : ad.type,
				"description" : ad.description,
				"valid_to" : ad.valid_to,
				"photo" : 'nistaa',
				"price" : ad.price
			}
		});
	}
	
	funZoneService.changeOffer = function(offer) {

		return $http({
			method : 'POST',
			url : 'funZone/changeOffer',
			data : {
				"id" : offer.id,
				"offer" : offer.offer,
				"user" : offer.user,
				"ad" : offer.ad
			}
		});
	}
	
	funZoneService.changeRatings = function(rating) {

		return $http({
			method : 'POST',
			url : 'funZone/changeRatings',
			data : {
				"id" : rating.id,
				"forLogin" : rating.forLogin,
				"forBuyOfficialProp" : rating.forBuyOfficialProp,
				"forBuyUsedProp" : rating.forBuyUsedProp,
				"forSendOffer" : rating.forSendOffer
			}
		});
	}
	
	funZoneService.removeOfficialAd = function(ad) {
		console.log(ad);

		return $http({
			method : 'POST',
			url : 'funZone/removeOfficialAd',
			data : {
				"id" : ad.id,
				"name" : ad.name,
				"type" : ad.type,
				"description" : ad.description,
				"valid_to" : ad.valid_to,
				"photo" : 'nistaa',
				"price" : ad.price
			}
		});
	}
	
	funZoneService.registerFunZoneAdmin = function(user) {
		console.log('usaoooo');
		return $http({
			method : 'POST',
			url : 'funZone/registerFunZoneAdmin',
			data : {
				"name" : user.name,
				"userRole" : "ADMIN_OF_FAN_ZONE",
				"surname" : user.surname,
				"password" : user.password,
				"email" : user.email,
				"city" : user.city,
				"phone" : user.phone
			}
		});
	}
	
	funZoneService.registerAdmin = function(user) {
		console.log('usaoooo');
		return $http({
			method : 'POST',
			url : 'funZone/registerAdmin',
			data : {
				"name" : user.name,
				"userRole" : "ADMIN",
				"surname" : user.surname,
				"password" : user.password,
				"email" : user.email,
				"city" : user.city,
				"phone" : user.phone
			}
		});
	}
	
	funZoneService.registerSystemAdmin = function(user) {
		console.log('usaoooo');
		return $http({
			method : 'POST',
			url : 'systemAdmin/registerSystemAdmin',
			data : {
				"name" : user.name,
				"userRole" : "ADMIN",
				"surname" : user.surname,
				"password" : user.password,
				"email" : user.email,
				"city" : user.city,
				"phone" : user.phone
			}
		});
	}
	
	
	funZoneService.changePassword = function(user) {
		console.log('usaoooo');
		return $http({
			method : 'POST',
			url : 'funZone/changePassword',
			data : {
				"password" : user.password,
			}
		});
	}
	
	funZoneService.acceptOfferTime = function() {
		return $http({
			method : 'GET',
			url : 'funZone/acceptOfferTime'
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
				'$interval',
				'FunZoneService',
				function($rootScope, $scope, $location, $route, $interval,
						 funZoneService) {
				
					$scope.showView = function(number){
						$scope.show = number;
					}
					
					$scope.showUsedProps = function(number){
						var today = new Date();
						var dd = today.getDate();
						var mm = today.getMonth()+1; //January is 0!
						var yyyy = today.getFullYear();
						 if(dd<10){
						        dd='0'+dd
						    } 
						    if(mm<10){
						        mm='0'+mm
						    } 

						today = yyyy+'-'+mm+'-'+dd;
						console.log(today);
						document.getElementById("date").setAttribute("min", today);
						$scope.showUsedPropsNum = number;
					}
					
					$scope.showOfficialProps = function(number){
						$scope.showOfficialPropsNum = number;
					}
					
					$scope.viewOffers = function(number){
						$scope.showOffers = number;
					}
					
					$scope.setSelected = function(selected) {
						$scope.selected = selected;
						
						
						funZoneService.getOffersOfAd(selected.id).then(function(response) {

							$scope.offers = response.data;
						}, function myError(response) {
							
					    });
					}
					
					$scope.setSelectedOffer = function(selected) {
						$scope.selectedOffer = selected;
					}
					
					$scope.change = function(change) {
						$scope.adForChange = change;
						$scope.showChange = 1;
						$('#changeName').val(change.name);
						$('#changeDes').val(change.description);
						$('#changePrice').val(change.price);
					}
					
					$scope.changeShow = function(change) {
						$scope.offerForChange = change;
						$scope.showChangeOffer = 1;
						$('#changeOfferPrice').val(change.offer);
					}
					
					$scope.uploadPhoto = function(){
						let image = $('#photo').prop('files')[0];
						console.log(image);
			            var formData = new FormData();
			            formData.append("image", image);
			            
			            funZoneService.uploadPhoto(formData
								).then(
								function(response) {
									if (response.data) {
										$scope.image_url = JSON.parse(response.data).data.link;
					                    alert("Uspesno aploadovana.");
									}
								}, function myError(response) {
									
							    });
					}
					
					$scope.createAd = function() {
						var ad = $scope.ad;
						ad.photo = photo;
						photo = "";
						funZoneService.createAd(
								ad).then(
								function(response) {
									if (response.data) {
										alert('Successfuly create ad')
										$scope.showUsedProps = null;
										$route.reload();
									}
								}, function myError(response) {
									
							    });
					}
					
					$scope.createOfficialAd = function() {
						var ad = $scope.ad;
						funZoneService.createOfficialAd(
								ad).then(
								function(response) {
									if (response.data != '') {
										alert('Successfuly create ad')
										$scope.showUsedProps = null;
										$route.reload();
									}else{
										alert('User must be admin');
									}
								}, function myError(response) {
									
							    });
					}
					
					$scope.sendOffer = function(offer) {
						funZoneService.sendOffer(
								offer,$scope.selected.id).then(
								function(response) {
									if (response.data != '') {
										alert('Successfuly sent offer');
										$scope.showUsedProps = null;
									}else{
										alert('You alredy sent offer');
									}
								}, function myError(response) {
									
							    });
					}
					
					funZoneService.getUsedApprovedAds().then(function(response) {

						$scope.ads = response.data;
						
					}, function myError(response) {
						
				    });
				
					funZoneService.getOfficialAds().then(function(response) {

						$scope.officialAds = response.data;
						
					}, function myError(response) {
						
				    });
					
					funZoneService.getAdsForApproved().then(function(response) {

						$scope.adsForApproved = response.data;
						
					}, function myError(response) {
						
				    });
					
					funZoneService.getCreditRating().then(function(response) {

						$scope.ratingForChange = response.data;
						$('#changeLoginRating').val(response.data.forLogin);
						$('#changeBuyOfficialPropRating').val(response.data.forBuyOfficialProp);
						$('#changeBuyUsedPropRating').val(response.data.forBuyUsedProp);
						$('#changeSendOfferRating').val(response.data.forSendOffer);

					}, function myError(response) {
						
				    });
					
					$scope.approvedAd = function(ad) {
						funZoneService.approvedAd(
								ad).then(
								function(response) {
									if (response.data) {
										alert('Successfuly approved ad');
										$route.reload();
									}
								}, function myError(response) {
									
							    });
					}
					
					$scope.rejectedAd = function(ad) {

						funZoneService.rejectedAd(
								ad).then(
								function(response) {
									if (response.data) {
										alert('Successfuly deleted ad');
										$route.reload();
									}
								}, function myError(response) {
									
							    });
					}
					
					$scope.buyProp = function(ad) {
console.log(ad);
						funZoneService.buyProp(
								ad).then(
								function(response) {
									if (response.data) {
										alert('Successfuly buy ad');
										$route.reload();
									}
								}, function myError(response) {
									
							    });
					}
					
					$scope.changeAd = function() {
						var ad = $scope.ad;
						console.log(ad);

						$scope.adForChange.name = $('#changeName').val();
						$scope.adForChange.description = $('#changeDes').val();
						$scope.adForChange.price = $('#changePrice').val();
						console.log($scope.adForChange);
						funZoneService.changeAd(
								$scope.adForChange).then(
								function(response) {
									if (response.data != '') {
										alert('Successfuly change ad');
										$route.reload();
									}else{
										alert('Only admin can do this');
									}
								}, function myError(response) {
									
							    });
					}

					$scope.changeRatings = function() {

						console.log($('#changeLoginRating').val());
						$scope.ratingForChange.forLogin = $('#changeLoginRating').val();
						$scope.ratingForChange.forBuyOfficialProp = $('#changeBuyOfficialPropRating').val();
						$scope.ratingForChange.forBuyUsedProp = $('#changeBuyUsedPropRating').val();
						$scope.ratingForChange.forSendOffer = $('#changeSendOfferRating').val();

						console.log($scope.ratingForChange);
						funZoneService.changeRatings(
								$scope.ratingForChange).then(
								function(response) {
									if (response.data != '') {
										alert('Successfuly change rating');
										$route.reload();
									}else{
										alert('Only admin can do this');
									}
								}, function myError(response) {
									
							    });
					}

					
					$scope.changeOffer = function(offer) {
						//var offer = $scope.offer;
						//console.log(offer);

						$scope.offerForChange.offer = $('#changeOfferPrice').val();
						console.log($scope.offerForChange);
						funZoneService.changeOffer(
								$scope.offerForChange).then(
								function(response) {
									if (response.data != '') {
										alert('Successfuly change offer');
										$route.reload();
									}else{
										alert('This is not your offer');
									}
								}, function myError(response) {
									
							    });
					}
					
					$scope.remove = function(ad) {

						funZoneService.removeOfficialAd(
								ad).then(
								function(response) {
									if (response.data != '') {
										alert('Successfuly remove ad');
										$route.reload();
									}else{
										alert('Only admin can do this');
									}
								}, function myError(response) {
									
							    });
					}
					$scope.acceptOffer = function(offer) {
						console.log(offer.id);
						funZoneService.acceptOffer(
								offer.id).then(
								function(response) {
									console.log(response.data);
									if (response.data != '') {
										alert('Successfuly accept offer');
										$route.reload();
									}else{
										alert('Only creator of ad can accept offer');
										$route.reload();
									}
								}, function myError(response) {
									
							    });
					}
					
					
					$scope.registerFunZoneAdmin = function() {
						var newCinemaAdmin = $scope.newCinemaAdmin;
						console.log($scope.newCinemaAdmin);
						funZoneService.registerFunZoneAdmin(
								newCinemaAdmin).then(
								function(response) {
									if (response.data != null) {

										alert('Successfuly registrated fun zone admin');

									} 
									$scope.show = null;
									$scope.newCinemaAdmin = null;
									$route.reload();
								}, function myError(response) {
						
								});
						
					}
					
					$scope.registerAdmin = function() {
						
						var newCinemaAdmin = $scope.newCinemaAdmin;
						console.log($scope.newCinemaAdmin);
						funZoneService.registerAdmin(
								newCinemaAdmin).then(
								function(response) {
									if (response.data != null) {

										alert('Successfuly registrated new admin');

									} 
									$scope.show = null;
									$scope.newCinemaAdmin = null;
									$route.reload();
								}, function myError(response) {
						
								});
						
					}
					
					$scope.registerSystemAdmin = function() {
						console.log("Usao");
						var newCinemaAdmin = $scope.newCinemaAdmin;
						console.log($scope.newCinemaAdmin);
						funZoneService.registerSystemAdmin(
								newCinemaAdmin).then(
								function(response) {
									if (response.data != null) {

										alert('Successfuly registrated new system admin');

									} 
									$scope.show = null;
									$scope.newCinemaAdmin = null;
									$route.reload();
								}, function myError(response) {
						
								});
						
					}
					
					
					
					$scope.changePassword = function(user) {
						console.log("usaooooo");
						console.log(user.password);
						funZoneService.changePassword(
								user).then(
								function(response) {
									if (response.data != null) {

										alert('Successfuly change password');
										$location.path("/homePage");
									} 

								}, function myError(response) {
						
								});
						
					}
					
					$scope.prihvacena = $interval(function() {
					funZoneService.acceptOfferTime().then(function(response) {
						for(var i = 0; i < response.data.length; i++){
							console.log(response.data[i]);
						}
					}, function myError(response) {
						
				    });
					},60000);
					
					funZoneService.getLoggedInUser().then(function(response) {
						
						console.log(response.data);
						$scope.loginUserRole = 0;
						if(response.data.userRole == 'ADMIN'){
							$scope.loginUserRole = 1;
						}else if(response.data.userRole == 'ADMIN_OF_FAN_ZONE'){
							$scope.loginUserRole = 2;
						}
						
					}, function myError(response) {
						
				    });

				}	
		]);

function uploadImage() {
    let image = $('#photo').prop('files')[0];
    var formData = new FormData();
    formData.append("image", image);
    $.ajax({
        method: 'POST',
        headers: {
            'Authorization': 'Client-ID c98199048ba3773',
            'Accept': 'application/json'
        },
        url: 'https://api.imgur.com/3/image',
        data: formData,
        processData: false,
        contentType: false,
        mimeType: 'multipart/form-data',
        success: function(data) {
            image_url = JSON.parse(data).data.link;
            photo = image_url;
            alert("Uspesno aploadovana.");
        },
        error: function(data) {

            alert("Neuspesno.");
        }
    });
}

