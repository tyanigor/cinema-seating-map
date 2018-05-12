<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<br>
<div class="container" style="max-width: 400px">
    <div class="panel panel-default">
        <div class="panel-heading">Регистрация</div>
        <div class="panel-body">
            <form ng-submit="createUser()" ng-controller="RegistrationCtrl" name="registrationForm">
                <div ng-show="registrationForm.username.$error.unique || registrationForm.password2.$error.passwordInvalid" class="alert alert-danger" role="alert">
                    <div ng-show="registrationForm.username.$error.unique">Пользователь уже существует!</div>
                    <div ng-show="registrationForm.password2.$error.passwordInvalid">Пароли не совпадают!</div>
                </div>
                <div class="form-group">
                    <input ng-model="username" ng-unique ng-unique-api="/user" name="username" class="form-control" class="form-control" placeholder="Пользователь" required autofocus>
                </div>
                <div class="form-group">
                    <input ng-model="password1" type="password" name="password1" class="form-control" placeholder="Пароль" required>
                </div>
                <div class="form-group">
                    <input ng-model="password2" ng-password-match="password1" type="password" name="password2" class="form-control" placeholder="Повторите пароль" required>
                </div>
                <button ng-disabled="registrationForm.$invalid" type="submit" class="btn btn-primary">Регистрация</button>
            </form>
        </div>
    </div>
</div>