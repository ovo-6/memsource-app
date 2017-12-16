var app = angular.module('MyApp',['ngMaterial', 'ngMessages']);


   app.controller("ProjectsCtrl", function($scope, $http, $rootScope) {

      $scope.projectsDisabled = function() {
        return !$rootScope.logged;
      };

      $scope.loadProjects = function() {
          $http.get('memsource/projects').
            success(function(data, status, headers, config) {
              $scope.projects = data;
              $scope.loaded = true;
              $scope.noError = true;
            }).
            error(function(data, status, headers, config) {
              $scope.loaded = true;
              $scope.noError = false;
              $scope.errorText = 'Error: ' + status + ' ' + data;
            });
        }
    });

    app.controller('SetupCtrl', function($scope, $http, $rootScope) {

      $scope.checkInputFields = function() {
        return $scope.username == null
                || $scope.username == ''
                || $scope.password == null
                || $scope.password == '';
      };


      $scope.submit = function() {
        var body = JSON.stringify({username:$scope.username, password:$scope.password});
        $http.put('memsource/config', body).
            success(function(data, status, headers, config) {
              $scope.success = true;
              $rootScope.logged = true;
            }).
            error(function(data, status, headers, config) {
              $scope.success = false;
            });
      };
    });
