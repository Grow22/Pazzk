const checkLogin = (event, link) => {
  // Get the login status from the body's data attribute
  const isLoggedIn = document.body.getAttribute("data-logged-in") === "true";

  // If the user is not logged in, prevent the default action and show an alert
  if (!isLoggedIn) {
    event.preventDefault();
    alert("로그인 후 이용 가능합니다.");
  }
};
