/**
 * Created by park on 15. 4. 17..
 */
var app = angular.module('love', []);

app.factory('$req', ['$http', function ($http) {
    var req = function (url, data, method) {
        if (method == undefined)
            method = "GET";

        if (data != undefined) {
            url += "?";
            url += parse(data);
        }

        function parse(obj) {
            var str = [];
            for (var p in obj)
                str.push(encodeURIComponent(p) + "="
                + encodeURIComponent(obj[p]));
            return str.join("&");
        }

        return $http({
            method: method,
            url: url,
            headers: {
                'Content-Type': 'application/x-www-form-urlencoded'
            }
        });
    };

    return req;
}]);