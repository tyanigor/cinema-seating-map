<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<br>
<div class="container" style="max-width: 60%">
    <div ng-controller="SessionsCtrl" ng-show="sessions.length" class="panel panel-default">
        <form>
            <input type="text" class="form-control" placeholder="Поиск..." ng-model="search">
        </form>
        <div ng-repeat="session in sessions | filter: search" class="list-group">
            <a href="#/session/{{session.id}}" class="list-group-item">
                <h4 class="list-group-item-heading">{{session.movie}}</h4>
                <p class="list-group-item-text">Номер зала {{session.hall.number}}. Время начала {{session.start | date : 'HH:mm'}}, окончание {{session.end | date : 'HH:mm'}}.</p>
            </a>
        </div>
    </div>
</div>
