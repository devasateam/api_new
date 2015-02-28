var OnliofliApp = angular.module('Onliofli', ['ngRoute','naif.base64'] );
OnliofliApp.config(['$routeProvider', '$httpProvider', function ($routeProvider,$httpProvider) {
    

    $routeProvider.when('/addBrand', {
        templateUrl: 'addbrand.html',
        controller:  'AddBrandController'
    });
}]);