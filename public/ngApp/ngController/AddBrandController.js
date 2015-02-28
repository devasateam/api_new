/*
    Controller to 
        Add brand
*/
OnliofliApp.controller('AddBrandController', function ($scope, $location, $http) {  
    $scope.AddBrandData = {
        "brand_name" : "",
        "brand_addressline1" : "",
        "brand_addressline2" : "",
        "brand_contact_name" : "",
        "brand_contact_number" : ""
    };
    $scope.addbrand = function(form){
            $scope.submitted = true;

          // If form is invalid, return and let AngularJS show validation errors.
          if (form.$invalid) {
            // bootbox.alert("Please Fill The Required Fields");
            return;
        }
        // freeze further action
        console.log('entered');
        jQuery('#overlay').show();
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
