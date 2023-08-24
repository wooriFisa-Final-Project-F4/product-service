// document.addEventListener("DOMContentLoaded", function () {
//   const productForm = document.getElementById("productForm");
//
//   productForm.addEventListener("submit", function (event) {
//     event.preventDefault();
//
//     const formData = new FormData(productForm);
//
//     fetch("/product/v1", {
//       method: "POST",
//       body: formData
//     })
//     .then(response => response.json())
//     .then(data => {
//       alert("Product registration successful!");
//       console.log(data);
//     })
//     .catch(error => {
//       alert("Error registering product");
//       console.error(error);
//     });
//   });
// });
