package main

import (
	"strconv"

	"github.com/gin-gonic/gin"
	"github.com/p2hacks2022/post-team05/internal/db/player"
	"github.com/p2hacks2022/post-team05/internal/db/spacetime"
)

func Ping(c *gin.Context) {
	c.JSON(200, gin.H{
		"message": "pong",
	})
}

// プレイヤーの状態を更新する
func UpdatePlayerStatus(c *gin.Context) {
	// パスパラメータ取得
	id, err := strconv.Atoi(c.Param("id"))
	if err != nil {
		c.JSON(500, gin.H{
			"message": "idが違います",
		})
		return
	}
	status, err := strconv.Atoi(c.Param("status"))
	if err != nil || status < 1 || 4 < status {
		c.JSON(500, gin.H{
			"message": "statusが違います",
		})
		return
	}

	// status更新
	player.UpdateStatus(id, status)
	c.JSON(200, gin.H{
		"message": "success",
	})
}

// 指定された時刻の(位置, 罠or人, プレイヤーid, 状態)を取得する
func GetSpaceTimes(c *gin.Context) {
	// param取得
	time := c.Query("time")

	// 返り値
	type JsonResponse struct {
		Latitude   float32
		Longtitude float32
		Altitude   float32
		ObjId      int // 人: player_id, 罠: player_id*(-1)
	}

	// 指定した時間の位置とObjを取得
	res, err := spacetime.GetByTime(time)
	if err != nil {
		c.JSON(500, gin.H{
			"message": err.Error(),
		})
		return
	}
	c.JSON(200, res)
}
