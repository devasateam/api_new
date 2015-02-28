/*
    Controller to 
        Add brand
*/
OnliofliApp.controller('AddBrandController', function ($scope, $location, $http) {  
    $scope.AddBrandData = {
        "brand_name" : "",
        "brand_logo" : "",
        "brand_addressline1" : "",
        "brand_addressline2" : "",
        "brand_contact_name" : "",
        "brand_contact_number" : ""
    };
    $scope.addbrand = function(){
        // checking validations
        // var b = $("form.addProperty").valid();
        // if(!(b)) {
        //     return false;
        // }
    	
    	$scope.AddBrandData.brand_logo=$scope.image.base64;
    	console.log('brand_logo');
    	console.log($scope.AddBrandData.brand_logo);
        $http({
            method  : 'POST',
            url     : '/addbrand',
            dataType : 'JSON',
            data    : $scope.AddBrandData
        }).success(function (result){
            jQuery('#overlay').hide();
            if(result.status_code == 200){
                bootbox.alert('Congratulations! Your brand has been added successfully.');

            }
            else if(result.status_code == 401){
                window.location.href="/";
            }
            else if(result.status_code == 400)
            {
                bootbox.alert(result.data.Reason);
            }
            else{
                window.location.href = "error";
            }
        });
    };

});
