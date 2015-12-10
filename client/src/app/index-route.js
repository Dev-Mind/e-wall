(function () {
  'use strict';

  angular.module('nw').config(function ($stateProvider, $urlRouterProvider, $locationProvider) {
    'ngInject';

    $locationProvider.html5Mode({
      enabled: true,
      rewriteLinks: false
    });

    $urlRouterProvider.otherwise('/home');

    //Router definition
    $stateProvider
      .state('home', new State('home', 'main/main.html').controller('MainCtrl').build());

  });

  //Create an object to use a fluent API to define routes
  function State(url, view) {
    this.state = {
      url: '/' + url,
      templateUrl: view
    };
  }

  State.prototype.roles = function (rights) {
    this.state.authorizedRoles = rights;
    return this;
  };
  State.prototype.resolve = function (resolve) {
    this.state.resolve = resolve;
    return this;
  };
  State.prototype.params = function (params) {
    this.state.params = params;
    return this;
  };
  State.prototype.data = function (data) {
    this.state.data = data;
    return this;
  };
  State.prototype.controller = function (controller) {
    this.state.controller = controller;
    this.state.controllerAs = 'ctrl';
    return this;
  };
  State.prototype.build = function () {
    return this.state;
  };
})();