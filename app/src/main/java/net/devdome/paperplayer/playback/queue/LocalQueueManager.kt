package net.devdome.paperplayer.playback.queue

import net.devdome.paperplayer.data.model.Song
import java.util.*

class LocalQueueManager : QueueManager {

    /**
     * Now Playing Queue
     */
    private val playingQueue: MutableList<QueueItem> = Collections.synchronizedList(ArrayList<QueueItem>())

    private var currentIndex: Int = 0

    private var queueActionListener: QueueManager.QueueActionListener? = null
    private var title: String? = null

    init {
        currentIndex = 0
        title = ""
    }

    override fun getCurrentIndex(): Int {
        return currentIndex
    }

    override fun setCurrentIndex(index: Int) {
        if (index >= 0 && index < playingQueue.size) {
            this.currentIndex = index
        }
    }

    override fun setQueue(songs: List<Song>, startSongId: Long) {
        setQueue("", songs, startSongId)
    }

    override fun setQueue(title: String, songs: List<Song>, startSongId: Long) {
        var startSongId = startSongId
        this.title = title
        if (startSongId.equals(0)) {
            startSongId = songs[0].id
        }
        playingQueue.clear()
        for (song in songs) {
            val item = QueueItem(song, song.id == startSongId)
            playingQueue.add(item)
            if (song.id == startSongId) {
                currentIndex = playingQueue.lastIndexOf(item)
            }
        }
    }

    override fun getQueueTitle(): String? {
        return null
    }

    override fun getCurrentSong(): Song {
        return playingQueue[currentIndex].song
    }

    override fun next(): Song? {
        return null
    }

    override fun previous(): Song? {
        return null
    }

    fun setQueueActionListener(queueActionListener: QueueManager.QueueActionListener) {
        this.queueActionListener = queueActionListener
    }
}