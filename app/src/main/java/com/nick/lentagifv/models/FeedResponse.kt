package com.nick.lentagifv.models

data class FeedResponse(
    val message: String,
    val result: Result
) {
    data class Result(
        val items: List<Item>,
        val lastId: Long,
        val lastSortingValue: Long
    ) {
        data class Item(
            val type: String,
            val `data`: Data?
        ) {
            data class Data(
                val id: Long,
                val type: Long,
                val author: Author,
                val subsiteId: Long,
                val subsite: Subsite,
                val title: String,
                val date: Long,
                val blocks: List<Block>?,
                val counters: Counters,
                val commentsSeenCount: Any?,
                val dateFavorite: Long,
                val hitsCount: Long,
                val isCommentsEnabled: Boolean,
                val isLikesEnabled: Boolean,
                val isFavorited: Boolean,
                val isRepost: Boolean,
                val likes: Likes,
                val isPinned: Boolean,
                val canEdit: Boolean,
                val etcControls: EtcControls,
                val repost: Any?,
                val isRepostedByMe: Boolean,
                val stackedRepostsAuthors: List<Any>,
                val subscribedToTreads: Boolean,
                val isShowThanks: Boolean,
                val isStillUpdating: Boolean,
                val isFilledByEditors: Boolean,
                val isEditorial: Boolean,
                val isPromoted: Boolean,
                val audioUrl: String,
                val commentEditor: CommentEditor,
                val coAuthor: Any?,
                val isBlur: Boolean
            ) {


                fun getMedia(): Block.Data.Item.Image.Data? {
                    return this.blocks?.findLast { it.type == "media" }?.data?.items?.get(0)?.image?.data
                }

                fun getYouTube(): Block.Data.Video? {
                    return this.blocks?.findLast { it.type == "video" }?.data?.video
                }


                data class Author(
                    val id: Long,
                    val type: Long,
                    val name: String,
                    val description: String,
                    val avatar: Avatar,
                    val cover: Cover?,
                    val isSubscribed: Boolean,
                    val isVerified: Boolean,
                    val isOnline: Boolean,
                    val isMuted: Boolean,
                    val isUnsubscribable: Boolean,
                    val isEnabledCommentEditor: Boolean,
                    val commentEditor: CommentEditor,
                    val isAvailableForMessenger: Boolean,
                    val prevEntry: PrevEntry
                ) {
                    data class Avatar(
                        val type: String,
                        val `data`: Data
                    ) {
                        data class Data(
                            val uuid: String,
                            val width: Long,
                            val height: Long,
                            val size: Long,
                            val type: String,
                            val color: String,
                            val hash: String,
                            val external_service: List<Any>
                        )
                    }

                    data class Cover(
                        val type: String,
                        val `data`: Data
                    ) {
                        data class Data(
                            val uuid: String,
                            val width: Long,
                            val height: Long,
                            val size: Long,
                            val type: String,
                            val color: String,
                            val hash: String,
                            val external_service: List<Any>
                        )
                    }

                    data class CommentEditor(
                        val enabled: Boolean
                    )

                    data class PrevEntry(
                        val id: Long,
                        val title: String,
                        val description: String
                    )
                }

                data class Subsite(
                    val id: Long,
                    val type: Long,
                    val name: String,
                    val description: String,
                    val avatar: Avatar,
                    val cover: Any?,
                    val isSubscribed: Boolean,
                    val isVerified: Boolean,
                    val isOnline: Boolean,
                    val isMuted: Boolean,
                    val isUnsubscribable: Boolean,
                    val isEnabledCommentEditor: Boolean,
                    val commentEditor: CommentEditor,
                    val isAvailableForMessenger: Boolean,
                    val prevEntry: PrevEntry
                ) {
                    data class Avatar(
                        val type: String,
                        val `data`: Data
                    ) {
                        data class Data(
                            val uuid: String,
                            val width: Long,
                            val height: Long,
                            val size: Long,
                            val type: String,
                            val color: String,
                            val hash: String,
                            val external_service: List<Any>
                        )
                    }

                    data class CommentEditor(
                        val enabled: Boolean
                    )

                    data class PrevEntry(
                        val id: Long,
                        val title: String,
                        val description: String
                    )
                }

                data class Block(
                    val type: String,
                    val `data`: Data,
                    val cover: Boolean,
                    val hidden: Boolean,
                    val anchor: String
                ) {
                    data class Data(
                        val items: List<Item>,
                        val with_background: Boolean,
                        val with_border: Boolean,
                        val text: String,
                        val text_truncated: String,
                        val video: Video,
                        val title: String
                    ) {
                        data class Item(
                            val title: String,
                            val author: String,
                            val image: Image
                        ) {
                            data class Image(
                                val type: String,
                                val `data`: Data
                            ) {
                                data class Data(
                                    val uuid: String,
                                    val width: Long,
                                    val height: Long,
                                    val size: Long,
                                    val type: String,
                                    val color: String,
                                    val hash: String,
                                    val external_service: List<Any>
                                )
                            }
                        }

                        data class Video(
                            val type: String,
                            val `data`: Data
                        ) {
                            data class Data(
                                val thumbnail: Thumbnail,
                                val width: Long,
                                val height: Long,
                                val time: Long,
                                val external_service: ExternalService
                            ) {
                                data class Thumbnail(
                                    val type: String,
                                    val `data`: Data
                                ) {
                                    data class Data(
                                        val uuid: String,
                                        val width: Long,
                                        val height: Long,
                                        val size: Long,
                                        val type: String,
                                        val color: String,
                                        val hash: String,
                                        val external_service: List<Any>
                                    )
                                }

                                data class ExternalService(
                                    val name: String,
                                    val id: String
                                )
                            }
                        }
                    }
                }


                data class Counters(
                    val comments: Int,
                    val favorites: Int,
                    val reposts: Int
                )

                data class Likes(
                    val summ: Long,
                    val counter: Long,
                    val isLiked: Long,
                    val isHidden: Boolean
                )

                data class EtcControls(
                    val editEntry: Boolean,
                    val pinContent: Boolean,
                    val unpublishEntry: Boolean
                )

                data class CommentEditor(
                    val enabled: Boolean
                )
            }
        }
    }
}