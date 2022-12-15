package controller

import (
	"strconv"

	"github.com/gin-gonic/gin"
	"github.com/p2hacks2022/post-team05/internal/model/player"
	"github.com/p2hacks2022/post-team05/internal/model/spacetime"
)

// ShowAccount godoc
// @Summary Show a account
// @Description get string by ID
// @Accept  json
// @Success 200 {object} gin.H{"message": "pong"}
// @Router /ping [get]
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
		c.JSON(400, gin.H{
			"message": "idが違います",
		})
		return
	}
	status, err := strconv.Atoi(c.Param("status"))
	if err != nil || status < 1 || 4 < status {
		c.JSON(400, gin.H{
			"message": "statusが違います",
		})
		return
	}

	// status更新
	err = player.UpdateStatus(id, status)
	if err != nil {
		c.JSON(500, gin.H{
			"message": err.Error(),
		})
		return
	}

	c.JSON(200, gin.H{
		"message": "success",
	})
}

// 指定された時刻の(位置, 罠or人, プレイヤーid, 状態)を取得する
func GetSpaceTimes(c *gin.Context) {
	// param取得
	time := c.Query("time")

	// 指定した時間の位置とObjを取得
	res, err := spacetime.GetByTime(time)
	if err != nil {
		c.JSON(400, gin.H{
			"message": err.Error(),
		})
		return
	}

	// 受け取ったものをJsonResponseに詰め直す
	type JsonResponse struct {
		Latitude   float32
		Longtitude float32
		Altitude   float32
		ObjId      int // 人: player_id, 罠: player_id*(-1)
	}
	jsonRes := make([]JsonResponse, len(res))
	for i := 0; i < len(res); i++ {
		jsonRes[i].Latitude = res[i].Latitude
		jsonRes[i].Longtitude = res[i].Longtitude
		jsonRes[i].Altitude = res[i].Altitude
		jsonRes[i].ObjId = res[i].ObjId
	}
	c.JSON(200, jsonRes)
}

// 指定された時刻の(位置, 罠or人, プレイヤーid, 状態)を格納する
func PostSpaceTimes(c *gin.Context) {
	// param取得
	type JsonRequest struct {
		Time       string  `json:"time"`
		Latitude   float32 `json:"latitude"`
		Longtitude float32 `json:"longtitude"`
		Altitude   float32 `json:"altitude"`
		ObjId      int     `json:"obj_id"`
	}
	var req JsonRequest
	err := c.ShouldBindJSON(&req)
	if err != nil {
		c.JSON(400, gin.H{
			"message": err.Error(),
		})
		return
	}

	// spacetimesに受け取ったパラメータをpostする
	err = spacetime.Post(req.Time, req.Latitude, req.Longtitude, req.Altitude, req.ObjId)
	if err != nil {
		c.JSON(500, gin.H{
			"message": err.Error(),
		})
		return
	}
	c.JSON(200, gin.H{
		"message": "success",
	})
}
