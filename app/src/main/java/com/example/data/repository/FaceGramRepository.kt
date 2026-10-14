package com.example.data.repository

import com.example.R
import com.example.data.model.*
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update

class FaceGramRepository {

    private val defaultUser = User(
        id = "u_1",
        fullName = "Niyon Islam Sohag",
        username = "niyonislamsohag",
        email = "smd224292@gmail.com",
        avatarResId = R.drawable.avatar_niyon,
        gender = "পুরুষ",
        country = "বাংলাদেশ 🇧🇩",
        birthDate = "01/05/2004",
        bio = "Digital Creator & Tech Enthusiast 🇧🇩\nFounder of FaceGram\nDM for collaboration ✨",
        postsCount = 3,
        followersCount = 1420,
        followingCount = 32,
        profileViews30Days = 17
    )

    private val _isLoggedIn = MutableStateFlow(true)
    val isLoggedIn: StateFlow<Boolean> = _isLoggedIn.asStateFlow()

    private val _currentUser = MutableStateFlow(defaultUser)
    val currentUser: StateFlow<User> = _currentUser.asStateFlow()

    private val _stories = MutableStateFlow<List<Story>>(emptyList())
    val stories: StateFlow<List<Story>> = _stories.asStateFlow()

    private val _posts = MutableStateFlow<List<Post>>(emptyList())
    val posts: StateFlow<List<Post>> = _posts.asStateFlow()

    private val _reels = MutableStateFlow<List<Reel>>(emptyList())
    val reels: StateFlow<List<Reel>> = _reels.asStateFlow()

    private val _exploreItems = MutableStateFlow<List<ExploreItem>>(emptyList())
    val exploreItems: StateFlow<List<ExploreItem>> = _exploreItems.asStateFlow()

    private val _suggestedUsers = MutableStateFlow<List<SuggestedUser>>(emptyList())
    val suggestedUsers: StateFlow<List<SuggestedUser>> = _suggestedUsers.asStateFlow()

    private val _comments = MutableStateFlow<Map<String, List<Comment>>>(emptyMap())
    val comments: StateFlow<Map<String, List<Comment>>> = _comments.asStateFlow()

    // Messenger State (must be initialized before init { loadInitialData() })
    private val _conversations = MutableStateFlow<List<Conversation>>(emptyList())
    val conversations: StateFlow<List<Conversation>> = _conversations.asStateFlow()

    private val _messages = MutableStateFlow<Map<String, List<ChatMessage>>>(emptyMap())
    val messages: StateFlow<Map<String, List<ChatMessage>>> = _messages.asStateFlow()

    private val _activeCall = MutableStateFlow<ActiveCall?>(null)
    val activeCall: StateFlow<ActiveCall?> = _activeCall.asStateFlow()

    init {
        loadInitialData()
    }

    private fun loadInitialData() {
        initMessengerData()
        _stories.value = listOf(
            Story(
                id = "story_own",
                username = "Your story",
                avatarResId = R.drawable.avatar_niyon,
                hasUnseen = false,
                isOwnStory = true,
                storyImageResId = R.drawable.feed_post,
                text = "Building the new FaceGram app! 🚀"
            ),
            Story(
                id = "story_1",
                username = "sohinisarkar01",
                avatarResId = R.drawable.reel_cover,
                hasUnseen = true,
                storyImageResId = R.drawable.reel_cover,
                text = "Happy weekend everyone! ✨",
                timeAgo = "1h"
            ),
            Story(
                id = "story_2",
                username = "koly.887",
                avatarResId = R.drawable.avatar_niyon,
                hasUnseen = true,
                storyImageResId = R.drawable.feed_post,
                text = "Good times with family ❤️",
                timeAgo = "3h"
            ),
            Story(
                id = "story_3",
                username = "nusratchi",
                avatarResId = R.drawable.reel_cover,
                hasUnseen = true,
                storyImageResId = R.drawable.reel_cover,
                text = "Dance rehearsal day! 💃",
                timeAgo = "5h"
            ),
            Story(
                id = "story_4",
                username = "maria_nur",
                avatarResId = R.drawable.avatar_niyon,
                hasUnseen = true,
                storyImageResId = R.drawable.feed_post,
                text = "Coffee and peaceful vibes ☕",
                timeAgo = "6h"
            )
        )

        _posts.value = listOf(
            Post(
                id = "post_1",
                userId = "u_arishba",
                username = "arishba.rahman_",
                userDisplayName = "Arishba Rahman",
                avatarResId = R.drawable.reel_cover,
                audioTrack = "arishba.rahman_ • Original audio",
                imageResId = R.drawable.feed_post,
                caption = "Celebrating wonderful moments with best people ❤️✨ #FaceGram #Dhaka #Moments",
                likesCount = 1420,
                commentsCount = 18,
                sharesCount = 51,
                bookmarksCount = 2613,
                isLiked = false,
                isBookmarked = false,
                timeAgo = "15m"
            ),
            Post(
                id = "post_2",
                userId = "u_1",
                username = "niyonislamsohag",
                userDisplayName = "Niyon Islam Sohag",
                avatarResId = R.drawable.avatar_niyon,
                audioTrack = "FaceGram Music • Trending Beats",
                imageResId = R.drawable.facegram_logo,
                caption = "Welcome to FaceGram! The future of social media is here 🚀 Built with love. Let me know your thoughts in the comments!",
                likesCount = 3890,
                commentsCount = 142,
                sharesCount = 312,
                bookmarksCount = 1200,
                isLiked = true,
                isBookmarked = true,
                timeAgo = "2h"
            ),
            Post(
                id = "post_3",
                userId = "u_ramisa",
                username = "ramisa25096",
                userDisplayName = "Ramisa",
                avatarResId = R.drawable.reel_cover,
                audioTrack = "Traditional Festive Beats",
                imageResId = R.drawable.reel_cover,
                caption = "Happy vibe! ✨☀️ Special occasions bring unforgettable smiles.",
                likesCount = 8940,
                commentsCount = 210,
                sharesCount = 98,
                bookmarksCount = 1520,
                isLiked = false,
                isBookmarked = false,
                timeAgo = "4h"
            )
        )

        _reels.value = listOf(
            Reel(
                id = "reel_1",
                username = "ramisa25096",
                avatarResId = R.drawable.reel_cover,
                audioTitle = "riya.dance • Filter grain",
                caption = "Happy Vibe! ✨☀️ Dancing to my favorite track #traditional #vibes #FaceGram",
                imageResId = R.drawable.reel_cover,
                likesCount = "24.5K",
                commentsCount = "1,842",
                sharesCount = "3.2K",
                bookmarksCount = "890",
                isLiked = false,
                isBookmarked = false,
                isFollowing = false
            ),
            Reel(
                id = "reel_2",
                username = "niyonislamsohag",
                avatarResId = R.drawable.avatar_niyon,
                audioTitle = "FaceGram Audio • Official Beats",
                caption = "Tech behind FaceGram! Building cool features everyday 💡 Join the journey!",
                imageResId = R.drawable.facegram_logo,
                likesCount = "52.1K",
                commentsCount = "4,210",
                sharesCount = "9.1K",
                bookmarksCount = "3.4K",
                isLiked = true,
                isBookmarked = true,
                isFollowing = true
            ),
            Reel(
                id = "reel_3",
                username = "sohinisarkar01",
                avatarResId = R.drawable.feed_post,
                audioTitle = "Sohini • Golden Hour",
                caption = "Sunset memories with friends 🌅 Enjoying every second!",
                imageResId = R.drawable.feed_post,
                likesCount = "31.8K",
                commentsCount = "920",
                sharesCount = "2.1K",
                bookmarksCount = "1.1K",
                isLiked = false,
                isBookmarked = false,
                isFollowing = false
            )
        )

        _exploreItems.value = listOf(
            ExploreItem("exp_1", R.drawable.feed_post, "Event celebration", "590K", isVideo = true, author = "binodon"),
            ExploreItem("exp_2", R.drawable.reel_cover, "Dance vibes", "61.5K", isVideo = true, author = "dance_club"),
            ExploreItem("exp_3", R.drawable.feed_post, "Style & Fashion", "427K", isVideo = false, author = "glam_bd"),
            ExploreItem("exp_4", R.drawable.facegram_logo, "FaceGram Tech Innovation", "10.3M", isVideo = true, author = "niyon"),
            ExploreItem("exp_5", R.drawable.feed_post, "Summer beach vibes", "7.6M", isVideo = false, author = "traveler"),
            ExploreItem("exp_6", R.drawable.reel_cover, "Action scene breakdown", "3.3M", isVideo = true, author = "cinema_fan"),
            ExploreItem("exp_7", R.drawable.feed_post, "Portrait moments", "3.4M", isVideo = false, author = "shutter"),
            ExploreItem("exp_8", R.drawable.reel_cover, "Salon styling trend", "4.2M", isVideo = true, author = "hair_art"),
            ExploreItem("exp_9", R.drawable.facegram_logo, "Micro arts & crafts", "9.4M", isVideo = true, author = "crafts_bd"),
            ExploreItem("exp_10", R.drawable.feed_post, "Futuristic tech design", "5.1M", isVideo = true, author = "futurism"),
            ExploreItem("exp_11", R.drawable.reel_cover, "Celebrity red carpet", "2.1M", isVideo = true, author = "fashion_hub")
        )

        _suggestedUsers.value = listOf(
            SuggestedUser("sug_1", "Maria Nur", "marianur", R.drawable.avatar_niyon, "1 mutual", false),
            SuggestedUser("sug_2", "Meghla", "meghla", R.drawable.reel_cover, "1 mutual", false),
            SuggestedUser("sug_3", "Alp", "alp.official", R.drawable.feed_post, "Suggested for you", false),
            SuggestedUser("sug_4", "Tanvir Ahmed", "tanvir_ahmed", R.drawable.avatar_niyon, "Followed by sohinisarkar01", false)
        )

        _comments.value = mapOf(
            "post_1" to listOf(
                Comment("c_1", "sohinisarkar01", R.drawable.reel_cover, "Looking so lovely! ❤️", "12m", 5),
                Comment("c_2", "niyonislamsohag", R.drawable.avatar_niyon, "Great photo! Welcome to FaceGram 🚀", "8m", 12),
                Comment("c_3", "koly.887", R.drawable.avatar_niyon, "Awesome vibes! 👏", "3m", 2)
            ),
            "post_2" to listOf(
                Comment("c_4", "ramisa25096", R.drawable.reel_cover, "Congratulations Niyon bhai! Great app! 🎉", "1h", 24),
                Comment("c_5", "marianur", R.drawable.avatar_niyon, "The design is super smooth! ❤️", "45m", 18)
            )
        )
    }

    fun login(emailOrUser: String, pass: String): Boolean {
        _isLoggedIn.value = true
        if (emailOrUser.isNotBlank()) {
            val username = if (emailOrUser.contains("@")) emailOrUser.substringBefore("@") else emailOrUser
            _currentUser.update {
                it.copy(
                    username = username.lowercase().replace(" ", "_"),
                    email = if (emailOrUser.contains("@")) emailOrUser else it.email
                )
            }
        }
        return true
    }

    fun signUp(
        fullName: String,
        username: String,
        email: String,
        gender: String,
        country: String,
        birthDate: String,
        avatarRes: Int?
    ) {
        _currentUser.value = User(
            id = "u_new_${System.currentTimeMillis()}",
            fullName = fullName.ifBlank { "Niyon Islam Sohag" },
            username = username.ifBlank { "niyonislamsohag" }.removePrefix("@"),
            email = email.ifBlank { "smd224292@gmail.com" },
            avatarResId = avatarRes ?: R.drawable.avatar_niyon,
            gender = gender,
            country = country,
            birthDate = birthDate,
            bio = "Hey there! I am using FaceGram ✨"
        )
        _isLoggedIn.value = true
    }

    fun logout() {
        _isLoggedIn.value = false
    }

    fun toggleLikePost(postId: String) {
        _posts.update { list ->
            list.map { post ->
                if (post.id == postId) {
                    val newLiked = !post.isLiked
                    val newCount = if (newLiked) post.likesCount + 1 else (post.likesCount - 1).coerceAtLeast(0)
                    post.copy(isLiked = newLiked, likesCount = newCount)
                } else post
            }
        }
    }

    fun toggleBookmarkPost(postId: String) {
        _posts.update { list ->
            list.map { post ->
                if (post.id == postId) {
                    val newBookmarked = !post.isBookmarked
                    val newCount = if (newBookmarked) post.bookmarksCount + 1 else (post.bookmarksCount - 1).coerceAtLeast(0)
                    post.copy(isBookmarked = newBookmarked, bookmarksCount = newCount)
                } else post
            }
        }
    }

    fun toggleLikeReel(reelId: String) {
        _reels.update { list ->
            list.map { reel ->
                if (reel.id == reelId) {
                    reel.copy(isLiked = !reel.isLiked)
                } else reel
            }
        }
    }

    fun toggleFollowReelUser(reelId: String) {
        _reels.update { list ->
            list.map { reel ->
                if (reel.id == reelId) {
                    reel.copy(isFollowing = !reel.isFollowing)
                } else reel
            }
        }
    }

    fun toggleBookmarkReel(reelId: String) {
        _reels.update { list ->
            list.map { reel ->
                if (reel.id == reelId) {
                    reel.copy(isBookmarked = !reel.isBookmarked)
                } else reel
            }
        }
    }

    fun toggleFollowSuggestedUser(userId: String) {
        _suggestedUsers.update { list ->
            list.map { user ->
                if (user.id == userId) {
                    user.copy(isFollowed = !user.isFollowed)
                } else user
            }
        }
    }

    fun dismissSuggestedUser(userId: String) {
        _suggestedUsers.update { list -> list.filter { it.id != userId } }
    }

    fun addComment(postId: String, text: String) {
        val user = _currentUser.value
        val newComment = Comment(
            id = "c_${System.currentTimeMillis()}",
            username = user.username,
            avatarResId = user.avatarResId,
            text = text,
            timeAgo = "Just now"
        )
        _comments.update { map ->
            val cur = map[postId].orEmpty()
            map + (postId to (listOf(newComment) + cur))
        }
        _posts.update { list ->
            list.map { post ->
                if (post.id == postId) post.copy(commentsCount = post.commentsCount + 1) else post
            }
        }
    }

    fun createPost(caption: String, imageRes: Int = R.drawable.feed_post) {
        val user = _currentUser.value
        val newPost = Post(
            id = "post_${System.currentTimeMillis()}",
            userId = user.id,
            username = user.username,
            userDisplayName = user.fullName,
            avatarResId = user.avatarResId,
            caption = caption,
            imageResId = imageRes,
            likesCount = 1,
            commentsCount = 0,
            isLiked = true,
            timeAgo = "Just now"
        )
        _posts.update { listOf(newPost) + it }
        _currentUser.update { it.copy(postsCount = it.postsCount + 1) }
    }

    fun initMessengerData() {
        val convs = listOf(
            Conversation(
                id = "conv_group_1",
                title = "FaceGram Creators 🇧🇩",
                isGroup = true,
                participants = listOf("Niyon", "Maria", "Sohini", "Ramisa", "Tanvir"),
                avatarResId = R.drawable.facegram_logo,
                isOnline = true,
                lastMessage = "Ramisa: চলুন আজকে সবাই মিলে গ্রুপ ভিডিও কলে আসি! 🎉",
                lastMessageTime = "10:48 AM",
                unreadCount = 3
            ),
            Conversation(
                id = "conv_1",
                title = "Maria Nur",
                isGroup = false,
                participants = listOf("Maria Nur"),
                avatarResId = R.drawable.avatar_niyon,
                isOnline = true,
                lastMessage = "Hey Niyon! FaceGram এ অডিও ও ভিডিও কল ফিচার দারুন হয়েছে ❤️",
                lastMessageTime = "10:45 AM",
                unreadCount = 1
            ),
            Conversation(
                id = "conv_2",
                title = "Sohini Sarkar",
                isGroup = false,
                participants = listOf("Sohini Sarkar"),
                avatarResId = R.drawable.reel_cover,
                isOnline = true,
                lastMessage = "Voice message (0:18) 🎙️",
                lastMessageTime = "10:30 AM",
                unreadCount = 0
            ),
            Conversation(
                id = "conv_3",
                title = "Ramisa",
                isGroup = false,
                participants = listOf("Ramisa"),
                avatarResId = R.drawable.reel_cover,
                isOnline = false,
                lastMessage = "নতুন রিল ভিডিওর লিংক পাঠিয়েছি, চেক করে দেখুন ✨",
                lastMessageTime = "Yesterday",
                unreadCount = 0
            ),
            Conversation(
                id = "conv_4",
                title = "Tanvir Ahmed",
                isGroup = false,
                participants = listOf("Tanvir Ahmed"),
                avatarResId = R.drawable.avatar_niyon,
                isOnline = true,
                lastMessage = "ভাই মেসেঞ্জারে গ্রুপ কলিং ফিচারটা চমৎকার হয়েছে 👍",
                lastMessageTime = "Yesterday",
                unreadCount = 0
            )
        )
        _conversations.value = convs

        _messages.value = mapOf(
            "conv_group_1" to listOf(
                ChatMessage("m_g1", "conv_group_1", "Maria Nur", "সবাইকে FaceGram গ্রুপে স্বাগতম! 🚀", type = com.example.data.model.MessageType.TEXT, timestamp = "10:35 AM", isFromMe = false),
                ChatMessage("m_g2", "conv_group_1", "Niyon Islam Sohag", "ধন্যবাদ মারিয়া! এখন সবাই টেক্সট, ভয়েস, ছবি ও ভিডিও পাঠাতে পারবেন।", type = com.example.data.model.MessageType.TEXT, timestamp = "10:38 AM", isFromMe = true),
                ChatMessage("m_g3", "conv_group_1", "Sohini", null, mediaResId = R.drawable.feed_post, type = com.example.data.model.MessageType.IMAGE, timestamp = "10:40 AM", isFromMe = false),
                ChatMessage("m_g4", "conv_group_1", "Tanvir", "ভয়েস মেসেজ শুনুন সবাই 🎙️", type = com.example.data.model.MessageType.VOICE_NOTE, voiceDuration = "0:24", timestamp = "10:42 AM", isFromMe = false),
                ChatMessage("m_g5", "conv_group_1", "Ramisa", "চলুন আজকে সবাই মিলে গ্রুপ ভিডিও কলে আসি! 🎉", type = com.example.data.model.MessageType.TEXT, timestamp = "10:48 AM", isFromMe = false)
            ),
            "conv_1" to listOf(
                ChatMessage("m_1", "conv_1", "Maria Nur", "শুভ সকাল নিয়ন ভাই! কেমন আছেন?", type = com.example.data.model.MessageType.TEXT, timestamp = "10:30 AM", isFromMe = false),
                ChatMessage("m_2", "conv_1", "Niyon Islam Sohag", "আলহামদুলিল্লাহ ভালো! FaceGram এর কাজ কেমন লাগছে?", type = com.example.data.model.MessageType.TEXT, timestamp = "10:32 AM", isFromMe = true),
                ChatMessage("m_3", "conv_1", "Maria Nur", "অসাধারন! বিশেষ করে অডিও কল এবং ভিডিও কল ফিচার অনেক ফাস্ট!", type = com.example.data.model.MessageType.TEXT, timestamp = "10:35 AM", isFromMe = false),
                ChatMessage("m_4", "conv_1", "Maria Nur", null, type = com.example.data.model.MessageType.VOICE_NOTE, voiceDuration = "0:15", timestamp = "10:40 AM", isFromMe = false),
                ChatMessage("m_5", "conv_1", "Maria Nur", "Hey Niyon! FaceGram এ অডিও ও ভিডিও কল ফিচার দারুন হয়েছে ❤️", type = com.example.data.model.MessageType.TEXT, timestamp = "10:45 AM", isFromMe = false)
            ),
            "conv_2" to listOf(
                ChatMessage("m_6", "conv_2", "Sohini", "নতুন রিল আপলোড দিয়েছি!", type = com.example.data.model.MessageType.TEXT, timestamp = "10:20 AM", isFromMe = false),
                ChatMessage("m_7", "conv_2", "Sohini", null, mediaResId = R.drawable.reel_cover, type = com.example.data.model.MessageType.IMAGE, timestamp = "10:25 AM", isFromMe = false),
                ChatMessage("m_8", "conv_2", "Sohini", null, type = com.example.data.model.MessageType.VOICE_NOTE, voiceDuration = "0:18", timestamp = "10:30 AM", isFromMe = false)
            )
        )
    }

    fun sendTextMessage(conversationId: String, text: String) {
        val user = _currentUser.value
        val newMsg = ChatMessage(
            id = "m_${System.currentTimeMillis()}",
            conversationId = conversationId,
            senderName = user.fullName,
            text = text,
            type = com.example.data.model.MessageType.TEXT,
            timestamp = "Just now",
            isFromMe = true
        )
        _messages.update { map ->
            val cur = map[conversationId].orEmpty()
            map + (conversationId to (cur + newMsg))
        }
        _conversations.update { list ->
            list.map { conv ->
                if (conv.id == conversationId) conv.copy(lastMessage = "You: $text", lastMessageTime = "Just now") else conv
            }
        }
    }

    fun sendMediaMessage(conversationId: String, mediaResId: Int, isVideo: Boolean = false) {
        val user = _currentUser.value
        val newMsg = ChatMessage(
            id = "m_${System.currentTimeMillis()}",
            conversationId = conversationId,
            senderName = user.fullName,
            mediaResId = mediaResId,
            type = if (isVideo) com.example.data.model.MessageType.VIDEO else com.example.data.model.MessageType.IMAGE,
            timestamp = "Just now",
            isFromMe = true
        )
        _messages.update { map ->
            val cur = map[conversationId].orEmpty()
            map + (conversationId to (cur + newMsg))
        }
        _conversations.update { list ->
            list.map { conv ->
                if (conv.id == conversationId) conv.copy(lastMessage = "You sent a ${if (isVideo) "video" else "photo"}", lastMessageTime = "Just now") else conv
            }
        }
    }

    fun sendVoiceMessage(conversationId: String, duration: String = "0:12") {
        val user = _currentUser.value
        val newMsg = ChatMessage(
            id = "m_${System.currentTimeMillis()}",
            conversationId = conversationId,
            senderName = user.fullName,
            type = com.example.data.model.MessageType.VOICE_NOTE,
            voiceDuration = duration,
            timestamp = "Just now",
            isFromMe = true
        )
        _messages.update { map ->
            val cur = map[conversationId].orEmpty()
            map + (conversationId to (cur + newMsg))
        }
        _conversations.update { list ->
            list.map { conv ->
                if (conv.id == conversationId) conv.copy(lastMessage = "You sent a voice message ($duration)", lastMessageTime = "Just now") else conv
            }
        }
    }

    fun startCall(conversationId: String, type: com.example.data.model.CallType) {
        val conv = _conversations.value.find { it.id == conversationId } ?: return
        _activeCall.value = ActiveCall(
            callId = "call_${System.currentTimeMillis()}",
            conversationId = conversationId,
            title = conv.title,
            avatarResId = conv.avatarResId,
            type = type,
            participants = if (conv.isGroup) conv.participants else listOf("You", conv.title),
            isMuted = false,
            isSpeakerOn = type != com.example.data.model.CallType.AUDIO,
            isVideoEnabled = type != com.example.data.model.CallType.AUDIO
        )
    }

    fun toggleCallMute() {
        _activeCall.update { it?.copy(isMuted = !it.isMuted) }
    }

    fun toggleCallSpeaker() {
        _activeCall.update { it?.copy(isSpeakerOn = !it.isSpeakerOn) }
    }

    fun toggleCallVideo() {
        _activeCall.update { it?.copy(isVideoEnabled = !it.isVideoEnabled) }
    }

    fun flipCamera() {
        _activeCall.update { it?.copy(isFrontCamera = !it.isFrontCamera) }
    }

    fun endCall() {
        _activeCall.value = null
    }

    fun updateProfile(
        fullName: String,
        username: String,
        bio: String,
        avatarRes: Int?
    ) {
        _currentUser.update {
            it.copy(
                fullName = fullName,
                username = username.removePrefix("@"),
                bio = bio,
                avatarResId = avatarRes ?: it.avatarResId
            )
        }
    }
}

