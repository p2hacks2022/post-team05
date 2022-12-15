package main

import "github.com/p2hacks2022/post-team05/internal/controller"

func main() {
	router := controller.GetRouter()
	router.Run(":8080")
}
