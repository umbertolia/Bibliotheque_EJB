package customtags;

import java.io.IOException;

import javax.servlet.jsp.JspException;
import javax.servlet.jsp.JspWriter;
import javax.servlet.jsp.tagext.TagSupport;

import metier.constantes.ActionEnum;

public class EnumTag extends TagSupport {


	private static final long serialVersionUID = 2622600490980009364L;
		private ActionEnum actionEnum;

		@Override
		public int doStartTag() throws JspException {
			if (actionEnum != null) {
				try {
					JspWriter out = pageContext.getOut();
					out.write(actionEnum.getNomAction());
				} catch (IOException e) {
					 throw new JspException(e.getMessage());
				}
			}	
			 return EVAL_PAGE;
		}

		public ActionEnum getActionEnum() {
			return actionEnum;
		}

		public void setActionEnum(ActionEnum actionEnum) {
			this.actionEnum = actionEnum;
		}
		
		
		public int doEndTag()
		{
		return EVAL_PAGE;
		}
	
	
}
