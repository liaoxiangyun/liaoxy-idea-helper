package com.github.liaoxiangyun.ideaplugin.coderaminder.settings

import com.github.liaoxiangyun.ideaplugin.coderaminder.util.CalendarUtil
import com.intellij.openapi.components.PersistentStateComponent
import com.intellij.openapi.components.ServiceManager
import com.intellij.openapi.components.State
import com.intellij.openapi.components.Storage
import com.intellij.util.xmlb.XmlSerializerUtil
import org.gitlab.api.GitlabAPI
import org.gitlab.api.models.GitlabUser

/**
 * Supports storing the application settings in a persistent way.
 * The [State] and [Storage] annotations define the name of the data and the file name where
 * these persistent application settings are stored.
 */
@State(name = "com.github.liaoxiangyun.ideaplugin.coderaminder.settings.CodeSettingsState", storages = [Storage("coderaminder.xml")])
class CodeSettingsState : PersistentStateComponent<CodeSettingsState> {

    var userId = "John Q. Public"
    var enableStatus = false

    var origin = "http://gitlab.szewec.com/"

    /**
     * token
     */
    var token = ""

    /**
     * 公休日历
     */
    var calendar = ""

    /**
     * 时间
     */
    var reTime = "18:30"
    var branches = "develop"

    /**
     * 每日报告
     */
    var dailyReport = true


    var gitlabUser: GitlabUser = GitlabUser()

    open fun getUser(): GitlabUser {
        if (gitlabUser == null) {
            gitlabUser = GitlabAPI.connect(origin, token).user
        }
        return gitlabUser!!
    }

    override fun getState(): CodeSettingsState {
        return this
    }

    override fun loadState(state: CodeSettingsState) {
        XmlSerializerUtil.copyBean(state, this)
    }

    companion object {
        val instance: CodeSettingsState
            get() = ServiceManager.getService(CodeSettingsState::class.java)
    }

}