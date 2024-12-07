package com.condation.cms.modules.components;

/*-
 * #%L
 * components-module
 * %%
 * Copyright (C) 2024 CondationCMS
 * %%
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as
 * published by the Free Software Foundation, either version 3 of the
 * License, or (at your option) any later version.
 * 
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 * 
 * You should have received a copy of the GNU General Public
 * License along with this program.  If not, see
 * <http://www.gnu.org/licenses/gpl-3.0.html>.
 * #L%
 */

import com.condation.cms.api.configuration.configs.SiteConfiguration;
import com.condation.cms.api.extensions.TemplateModelExtendingExtensionPoint;
import com.condation.cms.api.feature.features.ConfigurationFeature;
import com.condation.cms.api.feature.features.IsDevModeFeature;
import com.condation.cms.api.feature.features.TemplateEngineFeature;
import com.condation.cms.api.template.TemplateEngine.Model;
import com.condation.modules.api.annotation.Extension;

@Extension(TemplateModelExtendingExtensionPoint.class)
public class ComponentsTemplateModelExtension extends TemplateModelExtendingExtensionPoint{

    @Override
    public void extendModel(Model model) {
        model.values.put(
            "components", 
            new ComponentFunction(
                getRequestContext().get(TemplateEngineFeature.class).templateEngine(),
                getRequestContext().has(IsDevModeFeature.class),
                Helpers.getTemplateFileExtension(getContext())
                )
        );
    }

    

}
