/**
 * Created by park on 15. 4. 18..
 */
app.controller('6types', ['$scope', '$req', function ($scope, $req) {
    $req('abc', {a: 1, b: 2}, "POST").error(function () {



        console.log("1")
    });
}]);