package actions

import com.intellij.openapi.actionSystem.AnAction
import com.intellij.openapi.actionSystem.AnActionEvent
import com.intellij.openapi.editor.Editor
import com.intellij.openapi.fileEditor.FileEditorManager
import com.intellij.openapi.fileEditor.TextEditor
import com.intellij.openapi.project.Project
import com.intellij.openapi.ui.Messages

class ShowSelectionAction : AnAction() {

    override fun actionPerformed(event: AnActionEvent) {
        event.project?.let { project ->
            getCurrentTextEditor(project)?.let { editor ->
                with(editor.selectionModel) {
                    Messages.showInfoMessage("($selectionStart ~ $selectionEnd): $selectedText", "Selection")
                }
            }
        }
    }
}

class SelectAction : AnAction() {

    override fun actionPerformed(event: AnActionEvent) {
        event.project?.let { project ->
            getCurrentTextEditor(project)?.let { editor ->
                val caretOffset = editor.caretModel.offset
                editor.selectionModel.setSelection(0, caretOffset)
                Messages.showInfoMessage("Select from start to current caret position", "Select")
            }
        }
    }

}


private fun getCurrentTextEditor(project: Project): Editor? {
    return FileEditorManager.getInstance(project)
            .selectedEditors
            .filterIsInstance<TextEditor>()
            .firstOrNull()
            ?.editor
}
