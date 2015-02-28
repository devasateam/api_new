/*
    Controller to 
        check authentication
        embedded in dashboard.html
*/
OnliofliApp.controller('DashboardController', function ($scope, $location, $http) {  

    jQuery('#overlay').show();
    $http({
        method  : 'GET',
        url     : '/authcheck',
        dataType : 'JSON',
    }).success(function (result){
        jQuery('#overlay').hide();
        if(result.status_code == 200){
            jQuery('#overlay').hide();
        }
        else if(result.status_code == 401){
            window.location.href="/signin.html";
        }
        else if(result.status_code == 400)
        {
            bootbox.alert(result.data.Reason);
        }
        else{
            window.location.href = "error";
        }
    });
    console.log('inside dashboard');
    $scope.logoutDash = function(){
        console.log('inside logout');
        $http({
            method  : 'GET',
            url     : '/logout',
            dataType : 'JSON',
        }).success(function (result){
            jQuery('#overlay').hide();
            if(result.status_code == 200){
                window.location.href="/signin.html";
            }
            else{
                window.location.href = "error";
            }
        });
    }

});
