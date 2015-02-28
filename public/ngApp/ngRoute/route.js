var OnliofliApp = angular.module('Onliofli', ['ngRoute', 'flow'] );
OnliofliApp.config(['$routeProvider', '$httpProvider', 'flowFactoryProvider', function ($routeProvider,$httpProvider,flowFactoryProvider) {
    
    flowFactoryProvider.on('catchAll', function (event) {
    console.log('catchAll', arguments);
    });

    $routeProvider.when('/addBrand', {
        templateUrl: 'add-brand.html',
        controller:  'AddBrandController'
    });
}]);