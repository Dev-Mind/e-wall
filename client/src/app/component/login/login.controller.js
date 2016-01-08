(function () {

  'use strict';

  angular.module('ew-structure').controller('LoginCtrl', function ($http, $state) {
    'ngInject';

    var ctrl = this;

    ctrl.login = function(){
      var data = 'username=' + encodeURIComponent(ctrl.credentials.username) + '&password=' + encodeURIComponent(ctrl.credentials.password);

      $http
        .post('api/public/login', data, {
          headers: {
            'Content-Type': 'application/x-www-form-urlencoded'
          },
          ignoreErrorRedirection: 'ignoreErrorRedirection'
        })
        .then(function(){
          $state.go('admin');
        })
        .catch(function(response){
          switch (response.data.type) {
            case 'USER_NOT_FOUND':
              ctrl.error = 'Votre mot de passe est incorrect. Cette partie de l\'application est réservée aux personnes de l\'école des mines et votre identifiant est la racine de votre mot de passe. Si votre email est prenom.nom@emse.fr, votre identifiant est prenom.nom';
              break;
            case   'UNAUTHORIZED':
              ctrl.error = 'Votre mot de passe est incorrect.';
              break;
            default:
              ctrl.error = 'Erreur lors de la connexion. Veuillez contacter un administrateur';
          }
        });
    };
  });

})();