package controller

import (
	"github.com/gin-gonic/gin"
	swaggerFiles "github.com/swaggo/files"
	ginSwagger "github.com/swaggo/gin-swagger"
)

func GetRouter() *gin.Engine {
	r := gin.Default()

	// sample
	r.GET("/ping", Ping)

	// sample
	r.POST("/throw", func(c *gin.Context) {
		username := c.PostForm("username")
		password := c.PostForm("password")
		c.JSON(200, gin.H{
			"username": username,
			"password": password,
		})
	})

	// v1
	v1 := r.Group("/api/v1")
	{
		// プレイヤーの状態を更新するAPI
		v1.POST("/players/:id/status/:status", UpdatePlayerStatus)

		// 指定された時刻の(位置, 罠or人, プレイヤーid, 状態)を返す
		v1.GET("/spacetimes", GetSpaceTimes)

		// 指定された時刻の(位置, 罠or人, プレイヤーid, 状態)を返す
		v1.POST("/spacetimes", PostSpaceTimes)
	}

	// swagger ui
	r.GET("/swagger/*any", ginSwagger.WrapHandler(swaggerFiles.Handler))

	return r
}
