function getGithubInfo(user) {
    var username='https://api.github.com/users/'+user;
    console.log(username);
    $.ajax({
        type: "GET",
        url: username,
        dataType: 'json',

    }).done(function(data){
        showUser(data);

    }).fail(function(){
        console.log("Please try again something went wrong");
        noSuchUser(user);
    });

}

function showUser(user) {

    document.getElementById('userimage').src=user.avatar_url;
    document.getElementById('txtname').innerText=user.name;
    document.getElementById('userid').innerText=user.id;
    document.getElementById('userurl').href=user.url;
    document.getElementById('userurl').innerText=user.html_url;
    document.getElementById('userrepository').innerText=user.public_repos;
    document.getElementById('userfollowers').innerText=user.followers;
    document.getElementById('userfollowing').innerText=user.following;
}

$(document).ready(function () {
    $(document).on('keypress', '#username', function (e) {

        if (e.which == 13) {
            //get what the user enters
            username = $(this).val();

            $(this).val("");

            getGithubInfo(username);

        }
    })
});
