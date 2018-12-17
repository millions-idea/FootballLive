function genEmojiList (type, emojiList) {
  let result = {}
  for (let name in emojiList) {
    let emojiMap = emojiList[name]
    let list = []
    for (let key in emojiMap) {
      list.push({
        type,
        name,
        key,
        img: emojiMap[key].img
      })
    }
    if (list.length > 0) {
      result[name] = {
        type,
        name,
        list,
        album: list[0].img
      }
    }
  }
  return result
}

window.emojiUtil =  {
  props: {
    type: String,
    scene: String,
    to: String
  },
  data () {
    return {
      currType: 'emoji',
      currAlbum: 'emoji'
    }
  },
  computed: {
    emoji () {
      return genEmojiList('emoji', window.emojiObj.emojiList)
    },
    pinup () {
      return genEmojiList('pinup', window.emojiObj.pinupList)
    },
    currEmoji () {
      if (this.currType === 'emoji') {
        return this.emoji[this.currAlbum]
      } else if (this.currType === 'pinup') {
        return this.pinup[this.currAlbum]
      }
      return []
    }
  },
  methods: {
    selectAlbum (album) {
      this.currType = album.type
      this.currAlbum = album.name
    },
    selectEmoji (emoji) {
    	console.log(emoji)
      if (this.type === 'emoji') {
        // 由触发父组件事件，增加表情文案
        this.$emit('add-emoji', emoji.key)
      } else if (this.type === 'pinup') {
        if (this.type === 'session') {
          this.$store.dispatch('sendMsg', {
            type: 'custom',
            scene: this.scene,
            to: this.to,
            pushContent: '[贴图表情]',
            content: {
              type: 3,
              data: {
                catalog: this.currAlbum,
                chartlet: emoji.key
              }
            }
          })
        } else if (this.type === 'chatroom') {
          this.$store.dispatch('sendChatroomMsg', {
            type: 'custom',
            pushContent: '[贴图表情]',
            content: {
              type: 3,
              data: {
                catalog: this.currAlbum,
                chartlet: emoji.key
              }
            }
          })
        }
        this.$emit('hide-emoji')
      }
    }
  }
}



$(function(){
	var html = "";
	for (var i = 0; i < emojiUtil.computed.emoji().emoji.list.length; i++) {
		var item =  emojiUtil.computed.emoji().emoji.list[i];
		html += '<span class="emoji-item" onclick=\'addEmoji(' + JSON.stringify(item) +  ')\'>';
		html += '<img src="' + item.img + '">';
		html += '</span>';
	}
	$(".cnt").hide();
	$(".cnt").html(html); 
	
})


function addEmoji(obj){
	var img = '<img src="' + obj.img + '" />';
	$(".message-input").append(img);
	$(".message-input").trigger("input");
}
