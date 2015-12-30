(function () {

  'use strict';

  angular.module('ew-admin').controller('UserCtrl', function ($http, USER_ROLES, users) {
    'ngInject';

    var ctrl = this;

    function refresh(){
      delete ctrl.error;
      delete ctrl.entity;

      $http
        .get('/api/public/user')
        .then(function(response){
          ctrl.users = response.data;
        });
    }

    ctrl.saveUser= function(){
      if(ctrl.entity){
        delete ctrl.error;

        $http
          .put('/api/secured/user', ctrl.entity)
          .then(function(){
            refresh();
          })
          .catch(function(response){
            switch(response.status){
              case 400:
                ctrl.error = 'Les données saisies sont incorrectes ' + response.data ? response.data.message : '';
                break;
              default:
                ctrl.error = 'Une erreur a été détectée lors de l\'enregistrement';
            }
          });
      }
    };

    ctrl.cancel = function(){
      delete ctrl.entity;
    };

    ctrl.update = function(id){
      $http
        .get('/api/public/user/' + id)
        .then(function(response){
          ctrl.entity = response.data;
        });
    };

    ctrl.updateRole = function(role){
      if(ctrl.entity.roles.indexOf(role)<0){
        ctrl.entity.roles.push(role);
      }
      else{
        ctrl.entity.roles.splice(ctrl.entity.roles.indexOf(role), 1);

      }
      //We need always to have public role
      if(ctrl.entity.roles.indexOf(USER_ROLES.public)<0){
        ctrl.entity.roles.push(USER_ROLES.public);
      }
    };


    ctrl.roles = USER_ROLES;
    ctrl.users = users;
  });


})();