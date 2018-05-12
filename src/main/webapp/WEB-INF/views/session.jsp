<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<br>
<div class="container" style="max-width: 60%">
    <div ng-controller="SessionCtrl" class="panel panel-default">
        <div class="panel-heading">
            <h4>{{session.movie}}</h4>
            Номер зала {{session.hall.number}}. Время начала {{session.start | date : 'HH:mm'}}, окончание {{session.end | date : 'HH:mm'}}.
        </div>
        <div class="panel-body">
            <div ng-show="error" class="alert alert-danger" role="alert">
                {{error}}
            </div>
            <div>
            <span class="label label-free">Свободные</span>
            <span class="label label-reserve">Занятые</span>
            <span class="label label-my-reserve">Забронированные вами</span>
            <span class="label label-selected">Выделенные</span>
            <hr>
            <table ng-repeat="row in rows" class="bs-seats-table">
                <tr>
                    <td>
                        <span class="bs-seats-header">Ряд №{{row.number}}</span>
                        <div ng-repeat="seat in row.seats" ng-click="seatSelect(seat)" class="bs-seats-{{seat.operation}} bs-seats-{{seat.selected}}">{{seat.number}}</div>
                    </td>
                </tr>
            </table>
            <hr>
            <button ng-click="reserveSeats(true)" class="btn btn-primary">Забронировать</button>
            <button ng-click="reserveSeats(false)" class="btn btn-primary">Снять бронь</button>
        </div>
    </div>
</div>