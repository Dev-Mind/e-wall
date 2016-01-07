(function () {

  'use strict';

  angular.module('ew').factory('SecurityService', function ($rootScope, $http, USER_ROLES) {
    'ngInject';

    function valid(authorizedRoles) {
      //If screen has a restrictive access we need to control the rights
      if(authorizedRoles && authorizedRoles.indexOf(USER_ROLES.all)<0) {
        $http.get('api/secured/role')
          .then(function(response){
            var roles = response.data;

            if(!isAuthorized(authorizedRoles, roles)){
              $rootScope.$broadcast('$ewError', {type : 'RIGHTS'});
            }
          })
          .catch(function(){
            $rootScope.$broadcast('$ewError', {type : 'RIGHTS'});
          });
      }
    }

    function isAuthorized(authorizedRoles, roles) {
      if (!angular.isArray(authorizedRoles)) {
        authorizedRoles = [authorizedRoles];
      }

      var isAuth = false;
      angular.forEach(authorizedRoles, function (authorizedRole) {
        var authorized = roles.indexOf(authorizedRole) !== -1;
        if (authorized || authorizedRole === '*') {
          isAuth = true;
        }
      });

      return isAuth;
    }

    function isAdmin(callback){
      $http.get('api/secured/role')
        .then(function(response) {
          callback(isAuthorized('ADMIN', response.data));
        })
        .catch(function(response) {
          console.log(response);
          callback(false);
        });
    }

    return {
      'valid': valid,
      'isAdmin': isAdmin
    };
  });

})();