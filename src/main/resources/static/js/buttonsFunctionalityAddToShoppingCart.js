Array.from(document.querySelectorAll("button.add-to-cart-button"))
    .forEach(button => {
        button.addEventListener("click", (ev) => {
            fetch(appPath + "ajax/add-product", {
                method: "POST",
                body: JSON.stringify({
                    productId: ev.target.getAttribute("data-product-id"),
                    units: 1
                }),
                headers: {
                    "Content-type": "application/json; charset=UTF-8"
                }
            })
                .then(value => {
                    console.log("Ok")
                    Swal.fire({
                        position: "top-end",
                        icon: "success",
                        title: "Product\nsuccessfully added ",
                        showConfirmButton: false,
                        timer: 1500
                    })
                })

                .catch(reason => {
                    Swal.fire({
                        position: "top-end",
                        icon: "error",
                        title: "Shit happens",
                        showConfirmButton: false,
                        timer: 1500
                    })
                    console.log("Error")
                })
        })
    });
