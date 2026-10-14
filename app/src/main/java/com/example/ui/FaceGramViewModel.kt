package com.example.ui

import androidx.lifecycle.ViewModel
import com.example.data.model.*
import com.example.data.repository.FaceGramRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow

enum class BottomTab {
    HOME, SEARCH, REELS, PROFILE
}

class FaceGramViewModel(
    private val repository: FaceGramRepository = FaceGramRepository()
) : ViewModel() {

    val isLoggedIn = repository.isLoggedIn
    val currentUser = repository.currentUser
    val stories = repository.stories
    val posts = repository.posts
    val reels = repository.reels
    val exploreItems = repository.exploreItems
    val suggestedUsers = repository.suggestedUsers
    val comments = repository.comments
    val conversations = repository.conversations
    val messages = repository.messages
    val activeCall = repository.activeCall

    private val _selectedTab = MutableStateFlow(BottomTab.HOME)
    val selectedTab: StateFlow<BottomTab> = _selectedTab.asStateFlow()

    private val _activeStory = MutableStateFlow<Story?>(null)
    val activeStory: StateFlow<Story?> = _activeStory.asStateFlow()

    private val _activeCommentsPostId = MutableStateFlow<String?>(null)
    val activeCommentsPostId: StateFlow<String?> = _activeCommentsPostId.asStateFlow()

    private val _showCreatePost = MutableStateFlow(false)
    val showCreatePost: StateFlow<Boolean> = _showCreatePost.asStateFlow()

    private val _showEditProfile = MutableStateFlow(false)
    val showEditProfile: StateFlow<Boolean> = _showEditProfile.asStateFlow()

    private val _showNotifications = MutableStateFlow(false)
    val showNotifications: StateFlow<Boolean> = _showNotifications.asStateFlow()

    private val _showDirectMessages = MutableStateFlow(false)
    val showDirectMessages: StateFlow<Boolean> = _showDirectMessages.asStateFlow()

    private val _showMenuScreen = MutableStateFlow(false)
    val showMenuScreen: StateFlow<Boolean> = _showMenuScreen.asStateFlow()

    private val _activeConversationId = MutableStateFlow<String?>(null)
    val activeConversationId: StateFlow<String?> = _activeConversationId.asStateFlow()

    private val _selectedExploreItem = MutableStateFlow<ExploreItem?>(null)
    val selectedExploreItem: StateFlow<ExploreItem?> = _selectedExploreItem.asStateFlow()

    private val _searchQuery = MutableStateFlow("")
    val searchQuery: StateFlow<String> = _searchQuery.asStateFlow()

    fun openConversation(convId: String) {
        _activeConversationId.value = convId
    }

    fun closeConversation() {
        _activeConversationId.value = null
    }

    fun sendTextMessage(convId: String, text: String) {
        repository.sendTextMessage(convId, text)
    }

    fun sendMediaMessage(convId: String, mediaResId: Int, isVideo: Boolean = false) {
        repository.sendMediaMessage(convId, mediaResId, isVideo)
    }

    fun sendVoiceMessage(convId: String, duration: String) {
        repository.sendVoiceMessage(convId, duration)
    }

    fun startAudioCall(convId: String) {
        repository.startCall(convId, com.example.data.model.CallType.AUDIO)
    }

    fun startVideoCall(convId: String) {
        repository.startCall(convId, com.example.data.model.CallType.VIDEO)
    }

    fun startGroupVideoCall(convId: String) {
        repository.startCall(convId, com.example.data.model.CallType.GROUP_VIDEO)
    }

    fun toggleCallMute() = repository.toggleCallMute()
    fun toggleCallSpeaker() = repository.toggleCallSpeaker()
    fun toggleCallVideo() = repository.toggleCallVideo()
    fun flipCamera() = repository.flipCamera()
    fun endCall() = repository.endCall()

    fun selectTab(tab: BottomTab) {
        _selectedTab.value = tab
    }

    fun openStory(story: Story) {
        _activeStory.value = story
    }

    fun closeStory() {
        _activeStory.value = null
    }

    fun openComments(postId: String) {
        _activeCommentsPostId.value = postId
    }

    fun closeComments() {
        _activeCommentsPostId.value = null
    }

    fun setCreatePostVisible(visible: Boolean) {
        _showCreatePost.value = visible
    }

    fun setEditProfileVisible(visible: Boolean) {
        _showEditProfile.value = visible
    }

    fun setNotificationsVisible(visible: Boolean) {
        _showNotifications.value = visible
    }

    fun setDirectMessagesVisible(visible: Boolean) {
        _showDirectMessages.value = visible
    }

    fun setMenuScreenVisible(visible: Boolean) {
        _showMenuScreen.value = visible
    }

    fun selectExploreItem(item: ExploreItem?) {
        _selectedExploreItem.value = item
    }

    fun updateSearchQuery(query: String) {
        _searchQuery.value = query
    }

    // Repository delegate actions
    fun login(emailOrUser: String, pass: String): Boolean = repository.login(emailOrUser, pass)

    fun signUp(
        fullName: String,
        username: String,
        email: String,
        gender: String,
        country: String,
        birthDate: String,
        avatarRes: Int?
    ) = repository.signUp(fullName, username, email, gender, country, birthDate, avatarRes)

    fun logout() {
        repository.logout()
        _selectedTab.value = BottomTab.HOME
    }

    fun toggleLikePost(postId: String) = repository.toggleLikePost(postId)
    fun toggleBookmarkPost(postId: String) = repository.toggleBookmarkPost(postId)
    fun toggleLikeReel(reelId: String) = repository.toggleLikeReel(reelId)
    fun toggleFollowReelUser(reelId: String) = repository.toggleFollowReelUser(reelId)
    fun toggleBookmarkReel(reelId: String) = repository.toggleBookmarkReel(reelId)
    fun toggleFollowSuggestedUser(userId: String) = repository.toggleFollowSuggestedUser(userId)
    fun dismissSuggestedUser(userId: String) = repository.dismissSuggestedUser(userId)
    fun addComment(postId: String, text: String) = repository.addComment(postId, text)
    fun createPost(caption: String) = repository.createPost(caption)
    fun updateProfile(fullName: String, username: String, bio: String, avatarRes: Int?) =
        repository.updateProfile(fullName, username, bio, avatarRes)
}
