'use strict';

angular.module('cinemaSeatingMapApp', ['ngRoute', 'ngResource', 'ngMessages'])
    /**
     * Конфигурация заголовков и роутинга
     */
    .config(['$routeProvider', '$httpProvider', function($routeProvider, $httpProvider) {
        $httpProvider
            .defaults
            .headers
            .common['X-Requested-With'] = 'XMLHttpRequest';
        $routeProvider
            .when('/', {
                templateUrl: '/template/login',
                controller: 'LoginCtrl'
            })
            .when('/sessions', {
                templateUrl: '/template/sessions',
                controller: 'SessionsCtrl'
            })
            .when('/session/:sessionId', {
                templateUrl: '/template/session',
                controller: 'SessionCtrl'
            })
            .when('/registration', {
                templateUrl: '/template/registration',
                controller: 'RegistrationCtrl'
            })
            .otherwise({
                redirectTo: '/'
            });
    }])

    /*
     * Контроллер страницы регистрации
     */
    .controller('RegistrationCtrl', ['$scope', '$http', '$location', function($scope, $http, $location) {
        $scope.createUser = function() {
            $http({
                method: 'POST',
                url: '/user',
                data: {
                    username: $scope.username,
                    password: $scope.password1,
                    role: 'USER'
                },
                headers: {
                    'Content-Type': 'application/json',
                    'Accept': 'text/plain'
                }
            })
            .then(function(response) {
                if (response.status == 200) {
                    $location.path('/');
                }
            });
        }
    }])

    /**
     * Директива для проверки уникальности поля
     */
    .directive('ngUnique', ['$http', function($http) {
        return {
            require: 'ngModel',
            link: function(scope, element, attrs, ctrl) {
                if (!attrs.ngUniqueApi) {
                    throw new Error('Ошибка, не указан API для проверки уникальности!');
                }

                scope.$watch(function() {
                    return ctrl.$modelValue;
                }, function(currentValue) {
                    if (!currentValue) {
                        return;
                    }

                    var url = attrs.ngUniqueApi.replace(/\/?$/, '/') + currentValue;
                    $http.get(url).success(function(response) {
                        if (response) {
                            // Если запись найдена, значит ошибка
                            ctrl.$setValidity('unique', false);
                        } else {
                            // Если не найдена, значит вводимое значение уникально
                            ctrl.$setValidity('unique', true);
                        }
                    });
                });
            }
        }
    }])

    /**
     * Директива для проверки на соответствие двух паролей
     */
    .directive('ngPasswordMatch', [function() {
        return {
            require: 'ngModel',
            link: function(scope, element, attrs, ctrl) {
                var checker = function() {
                    var password1 = scope.$eval(attrs.ngModel),
                        password2 = scope.$eval(attrs.ngPasswordMatch);

                    // Если второй пароль еще не ввели или пароли верны, то выводить ошибку не будем
                    return password1 == password2 || !password1;
                };

                scope.$watch(checker, function(result) {
                    ctrl.$setValidity('passwordInvalid', result);
                });
            }
        }
    }])

    /**
     * Контроллер входа
     */
    .controller('LoginCtrl', ['$scope', '$http', '$location', function($scope, $http, $location) {
        $scope.login = function() {
            $http({
                method: 'POST',
                url: '/authenticate',
                data: 'username=' + $scope.username + '&password=' + $scope.password,
                headers: {
                    'Content-Type': 'application/x-www-form-urlencoded',
                    'X-Login-Ajax-call': 'true'
                }
            })
            .then(function(response) {
                if (response.data == 'success') {
                    $location.path('/sessions');
                } else {
                    $scope.error = true;
                }
            });
        }
    }])

    /**
     * Контроллер для работы со списком сеансов
     */
    .controller('SessionsCtrl', ['$scope', '$http', function($scope, $http) {
        $scope.sessions = [];
        $http.get('/api/v1/sessions').success(function(response) {
            $scope.sessions = response;
        });
    }])

    /**
     * Контроллер для работы с сеансом по id
     */
    .controller('SessionCtrl', ['$scope', '$http', '$routeParams', function($scope, $http, $routeParams) {
        $scope.sessionId = $routeParams.sessionId;

        /**
         * Запрос сеанса
         */
        $http.get('/api/v1/sessions/' + $routeParams.sessionId).success(function(response) {
            $scope.session = response;
        });

        /**
         * Получим текущего пользователя
         */
        $http.get('/currentUser').success(function(response) {
            $scope.user = response;
        });

        /**
         * Устанавливает последнюю операцию производимую с местом
         *
         * @param seat - место
         */
        $scope.seatSetLastOperation = function(seat) {
            var lastOperationId = 0,
                operationType = 'FREE'; // по умолчанию

            $scope.operations.forEach(function(operation) {
                if (operation.seat.id == seat.id && operation.id > lastOperationId) {
                    if (operation.type == 'RESERVE' && operation.user.id == $scope.user.id) {
                        operationType = 'MY-' + operation.type;
                    } else {
                        operationType = operation.type;
                    }
                    lastOperationId = operation.id;
                }
            });
            seat.operation = operationType;
        };

        /**
         * Запрос и формирование данных: операций рядов и мест
         */
        $scope.loadData = function() {
            $http.get('/api/v1/sessions/' + $routeParams.sessionId + '/operations').success(function(response) {
                $scope.operations = response || []; // Операции проводимые по сеансу
                $http.get('/api/v1/sessions/' + $routeParams.sessionId + '/rows').success(function(response) {
                    $scope.rows = response; // Ряды
                    $scope.rows.forEach(function(row) {
                        $http.get('/api/v1/rows/' + row.id + '/seats').success(function (response) {
                            row.seats = response; // Места
                            row.seats.forEach(function(seat) {
                                $scope.seatSetLastOperation(seat);
                            });
                        });
                    });
                });
            });
        };
        $scope.loadData();

        /**
         * Выделение/снятие выделения с места
         *
         * @param seat - место
         */
        $scope.seatSelect = function(seat) {
            if (!seat.selected && seat.operation != 'RESERVE') { // Выделить можно только не забронированное место
                seat.selected = 'SELECTED';
            } else {
               seat.selected = '';
            }
        };

        /**
         * Бронирование места или снятие брони
         *
         * @param state - булевое бронировать/снять бронь
         */
        $scope.reserveSeats = function(state) {
            var postData = [];
            $scope.rows.forEach(function(row) {
                row.seats.forEach(function(seat) {
                    if (seat.selected) {
                        postData.push({
                            type: state ? 'RESERVE' : 'FREE',
                            seat: seat,
                            user: $scope.user
                        });
                    }
                });
            });

            $http({
                method: 'POST',
                url: '/api/v1/sessions/' + $scope.sessionId + '/operations',
                data: postData,
                headers: {
                    'Content-Type': 'application/json',
                    'Accept': 'text/plain'
                }
            })
            .then(function(response) {
                $scope.error = false;
                if (response.data.error) {
                    // Вывод ошибки
                    $scope.error = response.data.message;
                    if (response.data.type != 'IllegalArgumentException') {
                        return;
                    }
                }
                $scope.loadData();
            });
        };
    }]);
