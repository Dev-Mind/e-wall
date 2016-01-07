(function () {
  'use strict';

  angular.module('ew').config(function ($stateProvider, $urlRouterProvider, $locationProvider, USER_ROLES) {
    'ngInject';

    $locationProvider.html5Mode({
      enabled: true,
      rewriteLinks: false
    });

    $urlRouterProvider.otherwise('/home');

    //Router definition
    $stateProvider
      .state('admin', new State(USER_ROLES, 'admin', 'component/admin/admin.html').controller('AdminCtrl').roles([USER_ROLES.admin, USER_ROLES.writer]).build())
      .state('bigqrcode', new State(USER_ROLES, 'bigqrcode', 'component/bigqrcode/bigqrcode.html').controller('BigQRCodeCtrl').build())
      .state('category', new State(USER_ROLES, 'category', 'component/category/category.html').roles([USER_ROLES.admin]).controller('CategoryCtrl').build())
      .state('home', new State(USER_ROLES, 'home', 'component/main/main.html').build())
      .state('login', new State(USER_ROLES, 'login', 'component/login/login.html').controller('LoginCtrl').build())
      .state('monitor', new State(USER_ROLES, 'monitor', 'component/monitoring/monitoring.html').roles([USER_ROLES.admin]).controller('MonitoringCtrl').build())
      .state('parameter', new State(USER_ROLES, 'parameter', 'component/parameter/parameter.html').roles([USER_ROLES.admin]).controller('ParameterCtrl').build())
      .state('production', new State(USER_ROLES, 'production/:id', 'component/production/production.html').roles([USER_ROLES.admin, USER_ROLES.writer]).controller('ProductionCtrl').build())
      .state('productions', new State(USER_ROLES, 'productions', 'component/productions/productions.html').roles([USER_ROLES.admin, USER_ROLES.writer]).controller('ProductionsCtrl').build())
      .state('myproductions', new State(USER_ROLES, 'myproductions', 'component/myproductions/myproductions.html').roles([USER_ROLES.admin, USER_ROLES.writer]).controller('MyProductionsCtrl').build())
      .state('user', new State(USER_ROLES, 'user', 'component/user/user.html').roles([USER_ROLES.admin]).controller('UserCtrl')
        .resolve({
          /* @ngInject */
          users: function ($http) {
            return $http.get('/api/public/user').then(function(response){
                return response.data;
                });
          }
        })
        .build())
    .
    state('ewerror', new State(USER_ROLES, 'ewerror/{type}', 'component/error/error.html')
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
  function State(USER_ROLES, url, view) {
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
