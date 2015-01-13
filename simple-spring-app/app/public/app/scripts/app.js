'use strict';

/**
 * @ngdoc overview
 * @name downsampleApp
 * @description
 * # downsampleApp
 *
 * Main module of the application.
 */
angular
  .module('downsampleApp', [
    'ngResource',
    'ngRoute',
    'ngSanitize'
  ])
  .config(function ($routeProvider) {
    $routeProvider
      .when('/', {
        templateUrl: 'views/main.html',
        controller: 'MainCtrl'
      })
      .when('/about', {
        templateUrl: 'views/about.html',
        controller: 'AboutCtrl'
      })
      .otherwise({
        redirectTo: '/'
      });
  });
