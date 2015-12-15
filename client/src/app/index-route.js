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
      .state('admin', new State('admin', 'component/admin/admin.html').build())
      .state('bigqrcode', new State('bigqrcode', 'component/bigqrcode/bigqrcode.html').controller('BigQRCodeCtrl').build())
      .state('category', new State('category', 'component/category/category.html').controller('CategoryCtrl').build())
      .state('home', new State('home', 'component/main/main.html').controller('MainCtrl').build())
      .state('monitor', new State('monitor', 'component/monitoring/monitoring.html').controller('MonitoringCtrl').build())
      .state('parameter', new State('parameter', 'component/parameter/parameter.html').controller('ParameterCtrl').build())
      .state('production', new State('production', 'component/production/production.html').controller('ProductionCtrl').build())
      .state('public', new State('public', 'component/public/public.html').build())
      .state('nwerror', new State('nwerror/{type}', 'component/error/error.html')
        .params({
          error: {}
        })
        .controller(
        /* @ngInject */
        function ($scope, $stateParams) {
          $scope.error = $stateParams.error;
          $scope.type = $stateParams.type;
        })
        .build());

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
