<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<br>
<div class="container" style="max-width: 400px">
    <div class="panel panel-default">
        <div class="panel-heading">Вход</div>
        <div class="panel-body">
            <form ng-submit="login()" ng-controller="LoginCtrl" name="loginForm">
                <div ng-show="error" class="alert alert-danger" role="alert">
                    Неверный пользователь или пароль!
                </div>
                <div class="form-group">
                    <input ng-model="username" name="username" placeholder="Пользователь" class="form-control" required autofocus>
                </div>
                <div class="form-group">
                    <input ng-model="password" name="password" type="password" class="form-control" placeholder="Пароль" required>
                </div>
                <button ng-disabled="loginForm.$invalid" class="btn btn-primary" type="submit">Вход</button>&nbsp;
                <a href="#/registration"><button class="btn btn-primary">Регистрация</button></a>
            </form>
        </div>
    </div>
</div>